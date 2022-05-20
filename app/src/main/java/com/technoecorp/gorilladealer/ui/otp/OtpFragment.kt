package com.technoecorp.gorilladealer.ui.otp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.DealerValidationRequest
import com.technoecorp.gorilladealer.R
import com.technoecorp.gorilladealer.databinding.FragmentOtpBinding
import com.technoecorp.gorilladealer.extensions.showShortToast
import com.technoecorp.gorilladealer.ui.TechnoecorpApplication
import com.technoecorp.gorilladealer.ui.custom.CustomDialogClass
import com.technoecorp.gorilladealer.utils.Validator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class OtpFragment : Fragment() {

    @Inject
    lateinit var otpViewModelFactory: OtpViewModelFactory
    private lateinit var viewModel: OtpViewModel
    private var _binding: FragmentOtpBinding? = null
    private val binding: FragmentOtpBinding get() = _binding!!
    private lateinit var customDialogClass: CustomDialogClass
    private lateinit var bundle: Bundle
    private lateinit var refId: String
    private var _id: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOtpBinding.inflate(inflater, container, false)
        (requireActivity().application as TechnoecorpApplication).getOtpSubComponent().inject(this)
        viewModel = ViewModelProvider(this, otpViewModelFactory)[OtpViewModel::class.java]
        bundle = requireArguments()
        refId = bundle.getString("ref", "")
        _id = bundle.getInt("id")
        initView()
        initCollector()
        return binding.root
    }

    private fun initView() {
        customDialogClass = CustomDialogClass(requireActivity())
        binding.toolbar.linearBack.visibility = View.INVISIBLE
        binding.resendCode.visibility = View.INVISIBLE
        binding.btnContinue.setOnClickListener {
            if (Validator.validOtpCodeWithLength(binding.otpText, 4)) {
                val code = binding.otpText.text.toString()
                viewModel.verifyDealer(DealerValidationRequest(refId, _id, code))
            }
        }
    }

    private fun dealerData() {
        CoroutineScope(Dispatchers.Main).launch {
            (requireActivity().application as TechnoecorpApplication).updateDealer(viewModel.dealerData())
        }
    }

    private fun initCollector() {
        lifecycleScope.launchWhenCreated {
            viewModel.otpResponse.collectLatest {
                when (it) {
                    is ResultWrapper.Loading -> {
                        if (!customDialogClass.isShowing) {
                            customDialogClass.show()
                        }
                    }
                    is ResultWrapper.Success -> {
                        if (customDialogClass.isShowing) {
                            customDialogClass.dismiss()
                        }
                        dealerData()
                        binding.root.findNavController()
                            .navigate(R.id.action_otpFragment_to_dashboardActivity)
                        requireActivity().finish()
                    }
                    is ResultWrapper.Error -> {
                        if (customDialogClass.isShowing) {
                            customDialogClass.dismiss()
                        }
                        requireContext().showShortToast(it.message)
                    } else ->{}
                }
            }
        }
    }

}