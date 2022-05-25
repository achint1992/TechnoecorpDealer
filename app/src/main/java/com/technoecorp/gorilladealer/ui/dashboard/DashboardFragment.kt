package com.technoecorp.gorilladealer.ui.dashboard

import android.animation.AnimatorInflater
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.animation.addListener
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.github.drjacky.imagepicker.ImagePicker
import com.google.firebase.dynamiclinks.DynamicLink.AndroidParameters
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.gson.Gson
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.data.Dealer
import com.technoecorp.domain.domainmodel.request.DealerAnalyticalRequest
import com.technoecorp.domain.domainmodel.request.UpdateProfilePicRequest
import com.technoecorp.domain.domainmodel.request.WithdrawalMoneyRequest
import com.technoecorp.domain.domainmodel.response.DashboardAnalytics
import com.technoecorp.gorilladealer.R
import com.technoecorp.gorilladealer.databinding.FragmentDashboardBinding
import com.technoecorp.gorilladealer.extensions.showShortExceptionToast
import com.technoecorp.gorilladealer.extensions.showShortToast
import com.technoecorp.gorilladealer.extensions.toDashboardAnalytics
import com.technoecorp.gorilladealer.ui.TechnoecorpApplication
import com.technoecorp.gorilladealer.ui.custom.CustomDialogClass
import com.technoecorp.gorilladealer.utils.NetworkChecker
import com.technoecorp.gorilladealer.utils.PermissionUtils
import com.technoecorp.gorilladealer.utils.S3Uploader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.net.URI
import java.net.URISyntaxException
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class DashboardFragment : Fragment() {


    @Inject
    lateinit var dashboardViewModelFactory: DashboardViewModelFactory
    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null
    private val binding: FragmentDashboardBinding get() = _binding!!
    private lateinit var dashboardAdapter: DashboardAdapter
    private lateinit var customDialogClass: CustomDialogClass
    private var _dealer: Dealer? = null
    private val dealer: Dealer get() = _dealer!!
    private lateinit var dashboardRecent: ArrayList<String>
    private var index: Int = 0
    private lateinit var s3Uploader: S3Uploader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        (requireActivity().application as TechnoecorpApplication).getDashboardSubComponent()
            .inject(this)
        dashboardViewModel =
            ViewModelProvider(this, dashboardViewModelFactory)[DashboardViewModel::class.java]
        _dealer = (requireActivity().application as TechnoecorpApplication).getUpdateDealer()
        s3Uploader = S3Uploader(requireActivity().applicationContext)
        s3Uploader.init()

        initView()
        initCollector()
        dashboardViewModel.getRecentData(::recentDataRender)
        return binding.root
    }

    private fun initView() {
        customDialogClass = CustomDialogClass(requireActivity())

        dashboardAdapter = DashboardAdapter(
            requireContext(),
            ::showIncomeList,
            ::showDealerList,
            ::requestWithdrawalRequest
        )
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = dashboardAdapter

        if (NetworkChecker.isInternetAvailable(requireContext())) {
            dashboardViewModel.createListForDashboardData(
                DealerAnalyticalRequest(
                    dealerId = dealer.dealerId,
                    referCode = dealer.referCode!!
                )
            )
        } else {
            requireContext().showShortToast(getString(R.string.require_internet))
        }

        binding.facebookButton.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.facebook.com/Technoecorp-Service-Pvt-Ltd-112394931093289/")
            )
            startActivity(intent)
        }


        binding.instaButton.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.instagram.com/invites/contact/?i=11epqbcn446nk&utm_content=m92tp8r")
            )
            startActivity(intent)
        }


        binding.youtubeButton.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/channel/UC9PbG4n7hkRpekdNm0tKfkQ")
            )
            startActivity(intent)
        }


        binding.telegramButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/technoecorp"))
            startActivity(intent)
        }


        binding.userMobile.text = dealer.mobileNo
        binding.userName.text = dealer.name
        binding.userRefCode.text = dealer.referCode
        binding.userType.text = getString(R.string.dealer)
        dealer.profilePic?.let {
            if (it != "") {
                refreshImage(it)
            }
        }
        binding.userRefCode.setOnClickListener {
            shareIntent(dealer.referCode!!)
        }
        binding.profileImage.setOnClickListener {
            checkPermission()
        }

    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            if (!PermissionUtils.checkStorage(requireContext())) {
                PermissionUtils.requestStorage(requestPermissionLauncher)
            } else {
                selectImageFromGallery()
            }
        } else {
            selectImageFromGallery()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                selectImageFromGallery()
            }
        }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                try {
                    val file = File(URI(uri.toString()))
                    s3Uploader.uploadURL(
                        file.absolutePath,
                        "compress_" + dealer.mobileNo + "_" + dealer.dealerId
                            .toString() + ".jpg", ::updateProfileImageOnServer
                    )

                } catch (e: URISyntaxException) {
                    requireContext().showShortExceptionToast(e)
                }
                // Use the uri to load the image
            }
        }


    private fun updateProfileImageOnServer(imagePath: String) {
        dashboardViewModel.updateProfilePic(
            UpdateProfilePicRequest(
                dealer.dealerId,
                imagePath
            )
        )
    }


    private fun selectImageFromGallery() {
        if (NetworkChecker.isInternetAvailable(requireContext())) {
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
        } else {
            requireContext().showShortToast(getString(R.string.require_internet))
        }
    }


    private fun showIncomeList(dashboardAnalytics: DashboardAnalytics) {
        val bundle: Bundle = bundleOf(
            "dealers" to Gson().toJson(dashboardAnalytics.dealerIncomeData?.dealers),
            "title" to dashboardAnalytics.dealerIncomeData?.title,
            "isExport" to dashboardAnalytics.dealerIncomeData?.isExport
        )
        binding.root.findNavController()
            .navigate(R.id.action_dashboardFragment_to_incomeListActivity, bundle)
    }

    private fun showDealerList() {
        binding.root.findNavController()
            .navigate(R.id.action_dashboardFragment_to_myDealerActivity)
    }

    private fun requestWithdrawalRequest() {
        if (NetworkChecker.isInternetAvailable(requireContext())) {
            dashboardViewModel.createWithdrawalRequest(WithdrawalMoneyRequest(dealer.dealerId))
        } else {
            requireContext().showShortToast(getString(R.string.require_internet))
        }
    }

    private fun showDialog() {
        if (!customDialogClass.isShowing) {
            customDialogClass.show()
        }
    }

    private fun dismissDialog() {
        if (customDialogClass.isShowing) {
            customDialogClass.dismiss()
        }
    }

    private fun initCollector() {
        lifecycleScope.launchWhenCreated {
            dashboardViewModel.withDrawlResponse.collectLatest {
                when (it) {
                    is ResultWrapper.Loading -> {
                        showDialog()
                    }
                    is ResultWrapper.Error -> {
                        dismissDialog()
                        requireContext().showShortToast(it.message)
                    }
                    is ResultWrapper.Success -> {
                        dismissDialog()
                        it.data?.status?.let { status ->
                            val message: String? =
                                if (status) getString(R.string.withdrawal_request_success) else it.data?.message
                            requireContext().showShortToast(message)
                        }
                    }

                }
            }
        }

        lifecycleScope.launchWhenCreated {
            dashboardViewModel.dashboardAnalytics.collectLatest { dealerAnalysis ->
                when (dealerAnalysis) {
                    is ResultWrapper.Loading -> {
                        showDialog()
                    }
                    is ResultWrapper.Error -> {
                        dismissDialog()
                        requireContext().showShortToast(dealerAnalysis.message)
                    }
                    is ResultWrapper.Success -> {
                        dismissDialog()

                        dealerAnalysis.data?.status?.let { check ->
                            if (!check) {
                                requireContext().showShortToast(dealerAnalysis.data?.message)
                                return@let
                            }
                            dashboardViewModel.saveDashboardResponse(dealerAnalysis.data!!)
                            val dashboardData = dealerAnalysis.data?.toDashboardAnalytics()
                            dealerAnalysis.data?.data?.recentDealers?.let {
                                dashboardViewModel.saveRecentUsers(it)
                            }
                            dashboardData?.let {
                                dashboardAdapter.submitList(dashboardData)
                                if (dashboardData.isEmpty()) {
                                    requireContext().showShortToast("Unable to Populate List")
                                }
                            }
                        }
                    }

                }
            }
        }

        lifecycleScope.launchWhenCreated {
            dashboardViewModel.updateProfilePic.collectLatest {
                when (it) {
                    is ResultWrapper.Loading -> {
                        showDialog()
                    }
                    is ResultWrapper.Error -> {
                        dismissDialog()
                        requireContext().showShortToast(it.message)
                    }
                    is ResultWrapper.Success -> {
                        dismissDialog()
                        it.data?.status?.let { bool ->
                            if (!bool) {
                                requireContext().showShortToast(it.data?.message)
                                return@let
                            }
                            refreshImage(it.data?.data?.data?.dealer?.profilePic!!)
                            dashboardViewModel.saveData(it.data?.data?.data?.dealer!!)
                        }
                    }

                }
            }
        }
    }

    private fun refreshImage(path: String) {
        Glide.with(requireContext()).load(path).skipMemoryCache(true).apply(
            RequestOptions.diskCacheStrategyOf(
                DiskCacheStrategy.NONE
            )
        ).into(binding.profileImage)
    }

    private fun recentDataRender(data: ArrayList<String>) {
        if (data.isEmpty()) {
            binding.textAnimation.visibility = View.INVISIBLE
            return
        }
        this.dashboardRecent = data
        binding.textAnimation.visibility = View.VISIBLE
        "Welcome! ${dashboardRecent[index]}".also { binding.userText.text = it }
        val animation = AnimatorInflater.loadAnimator(requireContext(), R.animator.shake)
        animation.apply {
            setTarget(binding.textAnimation)
            this.addListener({
                Timer().schedule(6000) {
                    CoroutineScope(Dispatchers.Main).launch {
                        index++
                        if (index >= dashboardRecent.size) {
                            index = 0
                        }
                        "Welcome! ${dashboardRecent[index]}".also {
                            binding.userText.text = it
                        }
                        animation.start()
                    }

                }
            })
            this.start()
        }


    }

    private fun shareIntent(referCodeText: String) {
        // firebase gorilladealer.page.link deep link
        val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(Uri.parse("https://www.technoesoft.com/?referId=$referCodeText"))
            .setDomainUriPrefix("https://gorilladealer.page.link") // Open links with this app on Android
            .setAndroidParameters(
                AndroidParameters.Builder().build()
            ) // Open links with com.example.ios on iOS
            .buildDynamicLink()
        val dynamicLinkUri = dynamicLink.uri

        FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLongLink(dynamicLinkUri)
            .buildShortDynamicLink()
            .addOnCompleteListener(
                requireActivity()
            ) { task ->
                if (task.isSuccessful) {
                    // Short link created
                    val shortLink = task.result.shortLink
                    val intent = Intent(Intent.ACTION_SEND)
                    /*This will be the actual content you wish you share.*/
                    /*The type of the content is text, obviously.*/intent.type =
                        "text/plain"
                    /*Applying information Subject and Body.*/intent.putExtra(
                        Intent.EXTRA_SUBJECT,
                        "Share Refer Code"
                    )
                    intent.putExtra(Intent.EXTRA_TEXT, shortLink.toString())
                    /*Fire!*/startActivity(
                        Intent.createChooser(
                            intent,
                            "Share Your Refer Code"
                        )
                    )
                }
            }
    }


}