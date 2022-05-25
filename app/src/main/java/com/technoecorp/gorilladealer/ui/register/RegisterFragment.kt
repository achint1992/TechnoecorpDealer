package com.technoecorp.gorilladealer.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.DealerRegisterRequest
import com.technoecorp.gorilladealer.R
import com.technoecorp.gorilladealer.databinding.FragmentRegisterBinding
import com.technoecorp.gorilladealer.extensions.showShortToast
import com.technoecorp.gorilladealer.ui.TechnoecorpApplication
import com.technoecorp.gorilladealer.ui.custom.CustomDialogClass
import com.technoecorp.gorilladealer.utils.NetworkChecker
import com.technoecorp.gorilladealer.utils.Validator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class RegisterFragment : Fragment() {
    @Inject
    lateinit var registerViewModelFactory: RegisterViewModelFactory
    private var _binding: FragmentRegisterBinding? = null
    private val binding: FragmentRegisterBinding get() = _binding!!
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var customDialogClass: CustomDialogClass

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        (requireActivity().application as TechnoecorpApplication).getRegisterSubComponent()
            .inject(this)
        registerViewModel =
            ViewModelProvider(this, registerViewModelFactory)[RegisterViewModel::class.java]

        initView()
        getReferCode()
        initCollector()
        return binding.root
    }

    private fun getReferCode() {
        CoroutineScope(Dispatchers.Main).launch {
            val referCode = registerViewModel.getDealerReferCode()
            binding.refCode.setText(referCode)
        }
    }

    private fun initView() {
        customDialogClass = CustomDialogClass(requireActivity())
        binding.loginButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.registerButton.setOnClickListener {
            val checkRef: Boolean = Validator.validCodeWithOutLength(binding.refCode)
            val emailCheck = Validator.validEmailAddress(binding.userEmail)
            val mobileCheck = Validator.validMobileNumber(binding.userMobile)
            val nameCheck = Validator.validTextField(binding.userName, "UserName")
            val check = checkRef && emailCheck && mobileCheck && nameCheck

            if (NetworkChecker.isInternetAvailable(requireContext())) {
                if (check) {
                    val userName = binding.userName.text.toString()
                    val mobileNo = binding.userMobile.text.toString()
                    val refCode = binding.refCode.text.toString()
                    val email = binding.userEmail.text.toString()
                    val type = "dealer"
                    binding.registerButton.isClickable = false
                    registerViewModel.register(
                        DealerRegisterRequest(
                            userName,
                            mobileNo,
                            email,
                            type,
                            refCode
                        )
                    )
                }
            } else {
                requireContext().showShortToast(getString(R.string.require_internet))
            }

        }
    }

    private fun initCollector() {
        lifecycleScope.launchWhenCreated {
            registerViewModel.registerResponse.collectLatest {
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
                        it.data?.let { res ->
                            val bundle = bundleOf("ref" to res.refId, "id" to res.id)
                            binding.root.findNavController()
                                .navigate(R.id.action_registerFragment_to_otpFragment, bundle)
                        }
                    }
                    is ResultWrapper.Error -> {
                        if (customDialogClass.isShowing) {
                            customDialogClass.dismiss()
                        }
                        requireContext().showShortToast(it.message)
                    }
                }
                binding.registerButton.isClickable = true
            }

        }
    }


}