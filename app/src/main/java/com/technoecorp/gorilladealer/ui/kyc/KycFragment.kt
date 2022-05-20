package com.technoecorp.gorilladealer.ui.kyc

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.data.Dealer
import com.technoecorp.domain.domainmodel.data.Kyc
import com.technoecorp.gorilladealer.R
import com.technoecorp.gorilladealer.databinding.FragmentKycBinding
import com.technoecorp.gorilladealer.extensions.showShortExceptionToast
import com.technoecorp.gorilladealer.extensions.showShortToast
import com.technoecorp.gorilladealer.ui.TechnoecorpApplication
import com.technoecorp.gorilladealer.ui.custom.CustomDialogClass
import com.technoecorp.gorilladealer.utils.PermissionUtils
import com.technoecorp.gorilladealer.utils.S3Uploader
import com.technoecorp.gorilladealer.utils.Validator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.net.URI
import java.net.URISyntaxException
import javax.inject.Inject

class KycFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: KycViewModelFactory
    private lateinit var kycViewModel: KycViewModel

    private var _binding: FragmentKycBinding? = null
    private val binding: FragmentKycBinding get() = _binding!!

    private var _dealer: Dealer? = null
    private val dealer: Dealer get() = _dealer!!


    private lateinit var customDialogClass: CustomDialogClass
    private lateinit var s3Uploader: S3Uploader

    private var accountType: String = ""
    private var idProofPath = ""
    private var addProofPath = ""

    var dialog: AlertDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentKycBinding.inflate(inflater, container, false)
        (requireActivity().application as TechnoecorpApplication).getKycSubComponent()
            .inject(this)
        kycViewModel = ViewModelProvider(this, viewModelFactory)[KycViewModel::class.java]
        _dealer = (requireActivity().application as TechnoecorpApplication).getUpdateDealer()
        s3Uploader = S3Uploader(requireActivity().applicationContext)
        s3Uploader.init()
        initViews()
        initCollector()
        return binding.root
    }


    private fun initViews() {
        customDialogClass = CustomDialogClass(requireContext())
        val type = arrayOf("Saving", "Credit", "Current")
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.state_selected_item, R.id.stateName,
            type
        )
        binding.accountType.setAdapter(adapter)
        binding.accountType.setOnItemClickListener { adapterView, _, i, _ ->
            val item = adapterView.getItemAtPosition(i)
            item?.let {
                if (it is String) {
                    accountType = it
                }
            }
        }
        binding.uploadIdButton.setOnClickListener {
            checkPermission(launcherIdProof)
        }
        binding.uploadAddressButton.setOnClickListener {
            checkPermission(launcherAddress)
        }
        if (!isValidationProfile()) {
            showMaterialDialog()
        }

        if (dealer.kycDetail != null) {
            updateKycOnUI()
        }
        updateKycButton()
    }

    private fun checkPermission(launcher: ActivityResultLauncher<Intent>) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (!PermissionUtils.checkStorage(requireContext())) {
                PermissionUtils.requestStorage(requestPermissionLauncher)
            } else {
                selectImageFromGallery(launcher)
            }
        } else {
            selectImageFromGallery(launcher)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                requireContext().showShortToast("Kindly Try to upload document now..")
            }
        }

    private fun updateImageView(path: String?, imageView: ImageView) {
        Glide.with(requireContext()).load(path).apply(
            RequestOptions.diskCacheStrategyOf(
                DiskCacheStrategy.NONE
            )
        ).into(imageView)
    }

    private fun updateKycOnUI() {
        dealer.kycDetail?.let {
            Timber.e("data is ${it.toString()}")
        }
        dealer.kycDetail?.userVerificationId?.let {
            idProofPath = it
        }
        dealer.kycDetail?.addressProofId?.let {
            addProofPath = it
        }


        binding.accountName.setText(dealer.kycDetail?.accountName)
        binding.accountNumber.setText(dealer.kycDetail?.accountNumber)
        binding.confirmAccountNumber.setText(dealer.kycDetail?.accountNumber)
        binding.bankName.setText(dealer.kycDetail?.bankName)
        binding.branchName.setText(dealer.kycDetail?.branchName)
        binding.ifscCode.setText(dealer.kycDetail?.ifscCode)
        binding.accountType.setDropDownBackgroundResource(R.color.white)
        dealer.kycDetail?.accountType.let {
            binding.accountType.setText(it)
        }

        updateImageView(addProofPath, binding.addProofImage)
        updateImageView(idProofPath, binding.idProofImage)


        binding.registerButton.setOnClickListener {
            val accountNameCheck = Validator.validTextField(binding.accountName, "Account Name")
            val accountNumberCheck =
                Validator.validTextField(binding.accountNumber, "Account Number")
            val conAccountNumberCheck =
                Validator.validTextField(binding.confirmAccountNumber, "Confirm Account Number")
            val matchCheck: Boolean =
                if (accountNumberCheck && conAccountNumberCheck) Validator.matchTextField(
                    binding.accountNumber,
                    binding.confirmAccountNumber,
                    "Account Number and Confirm Account Number"
                ) else false
            val bankNameCheck = Validator.validTextField(binding.bankName, "Bank Name")
            val branchNameCheck = Validator.validTextField(binding.branchName, "Branch Name")
            val ifscCodeCheck = Validator.validTextField(binding.ifscCode, "IFSC")
            val idProofCheck = idProofPath.isNotEmpty()
            val addProofCheck = addProofPath.isNotEmpty()
            if (idProofCheck || addProofCheck) {
                requireContext().showShortToast("Kindly Upload Address and Id Proof")
            }
            if (addProofCheck && accountNameCheck && ifscCodeCheck && idProofCheck && matchCheck && bankNameCheck && branchNameCheck) {
                kycViewModel.uploadKycDetail(
                    Kyc(
                        dealer.dealerId,
                        binding.accountName.text.toString(),
                        binding.accountNumber.text.toString(),
                        binding.bankName.text.toString(),
                        binding.branchName.text.toString(),
                        binding.ifscCode.text.toString(),
                        accountType,
                        addProofPath,
                        idProofPath
                    )
                )
            }

        }
    }

    fun isValidationProfile(): Boolean {
        when {
            dealer.email.equals("", true) -> {
                return false
            }
            dealer.alternetMobileNo1.equals("", true) -> {
                return false
            }
            dealer.countryId == -1 -> {
                return false
            }
            dealer.stateId == -1 -> {
                return false
            }
            dealer.cityId == -1 -> {
                return false
            }
            dealer.userAddress.equals("", true) -> {
                return false
            }
            dealer.districtName.equals("", true) -> {
                return false
            }
            dealer.pincode.equals("", true) -> {
                return false
            }
            dealer.fatherName.equals("", true) -> {
                return false
            }
            dealer.dob.equals("", true) -> {
                return false
            }
            else -> return true
        }
    }

    private fun initCollector() {
        lifecycleScope.launchWhenCreated {
            kycViewModel.updateKyc.collectLatest { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        if (!customDialogClass.isShowing) {
                            customDialogClass.show()
                        }
                    }
                    is ResultWrapper.Success -> {
                        if (customDialogClass.isShowing) {
                            customDialogClass.dismiss()
                        }
                        result.data?.status?.let {
                            if (it) {
                                dealer.kycDetail = result.data?.data
                                kycViewModel.saveData(dealer)
                                (requireActivity().application as TechnoecorpApplication).updateDealer(
                                    dealer
                                )
                                CoroutineScope(Dispatchers.Main).launch {
                                    updateKycButton()
                                }
                            } else {
                                requireContext().showShortToast(result.data?.message)
                            }
                        }
                    }
                    is ResultWrapper.Error -> {
                        if (customDialogClass.isShowing) {
                            customDialogClass.dismiss()
                        }
                        requireContext().showShortToast(result.message)
                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun updateKycButton() {
        when {
            dealer.kycDetail == null -> {
                binding.registerButton.isClickable = true
            }
            dealer.kycDetail?.isKycVerify == 0 -> {
                //     binding.registerButton.isClickable = false
                requireContext().showShortToast("Your Kyc is under verification")
            }
            dealer.kycDetail?.isKycRejected == 1 -> {
                binding.registerButton.isClickable = true
                requireContext().showShortToast("Your Kyc is rejected. Please submit your details again")
            }
        }
    }

    fun showMaterialDialog() {
        dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Profile Update")
            .setMessage("Your profile details are not updated. You won't be able to receive any payment without updating it.")
            .setPositiveButton(
                "Edit Profile Now"
            ) { dialogInterface, _ ->
                binding.root.findNavController()
                    .navigate(R.id.action_kycFragment_to_editProfileFragment)
                dialogInterface.dismiss()
            }.setCancelable(false).show()
    }


    private val launcherAddress =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                try {
                    val file = File(URI(uri.toString()))
                    s3Uploader.uploadURL(
                        file.absolutePath,
                        "compress_" + dealer.mobileNo + "_" + dealer.dealerId
                            .toString() + "_add_proof.jpg", ::setAddressProof
                    )

                } catch (e: URISyntaxException) {
                    requireContext().showShortExceptionToast(e)
                }
                // Use the uri to load the image
            }
        }

    private val launcherIdProof =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                try {
                    val file = File(URI(uri.toString()))
                    s3Uploader.uploadURL(
                        file.absolutePath,
                        "compress_" + dealer.mobileNo + "_" + dealer.dealerId
                            .toString() + "_id_proof.jpg", ::setIdProof
                    )

                } catch (e: URISyntaxException) {
                    requireContext().showShortExceptionToast(e)
                }
                // Use the uri to load the image
            }
        }


    private fun setAddressProof(imagePath: String) {
        this.addProofPath = imagePath
        CoroutineScope(Dispatchers.Main).launch {
            updateImageView(addProofPath, binding.addProofImage)
        }

    }

    private fun setIdProof(imagePath: String) {
        this.idProofPath = imagePath
        CoroutineScope(Dispatchers.Main).launch {
            updateImageView(idProofPath, binding.idProofImage)
        }
    }


    private fun selectImageFromGallery(launcher: ActivityResultLauncher<Intent>) {
        val intent = ImagePicker.with(requireActivity())
            .galleryOnly()
            .crop() //Crop image(Optional), Check Customization for more option
            .maxResultSize(
                1080,
                1080
            ) //Final image resolution will be less than 1080 x 1080(Optional)
            .cropSquare()
            .createIntent()
        launcher.launch(intent)
    }


}