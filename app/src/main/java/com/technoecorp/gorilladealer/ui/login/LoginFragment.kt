package com.technoecorp.gorilladealer.ui.login

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
import com.technoecorp.gorilladealer.R
import com.technoecorp.gorilladealer.databinding.FragmentLoginBinding
import com.technoecorp.gorilladealer.extensions.showShortToast
import com.technoecorp.gorilladealer.ui.TechnoecorpApplication
import com.technoecorp.gorilladealer.ui.custom.CustomDialogClass
import com.technoecorp.gorilladealer.utils.NetworkChecker
import com.technoecorp.gorilladealer.utils.Validator
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


class LoginFragment : Fragment() {

    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory
    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var customDialogClass: CustomDialogClass

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        (requireActivity().application as TechnoecorpApplication).getLoginSubComponent()
            .inject(this)
        loginViewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]

        initView()
        initObservers()

        return binding.root
    }

    private fun initView() {
        customDialogClass = CustomDialogClass(requireActivity())
        binding.registerButton.setOnClickListener {
            //it.findNavController().navigate()
        }

        binding.loginButton.setOnClickListener {
            if (NetworkChecker.isInternetAvailable(requireContext())) {
                if (Validator.validMobileNumber(binding.userMobile)) {
                    binding.loginButton.isClickable = false
                    loginViewModel.loginDealer(binding.userMobile.text.toString(), "+91")
                }
            } else {
                requireContext().showShortToast(getString(R.string.require_internet))
            }
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

    private fun initObservers() {
        lifecycleScope.launchWhenCreated {
            loginViewModel.loginResponse.collectLatest {
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
                        it.data?.status?.let { res ->
                            if (res) {
                                val bundle = bundleOf("ref" to it.data?.refId, "id" to it.data?.id)
                                binding.root.findNavController()
                                    .navigate(R.id.action_loginFragment_to_otpFragment, bundle)
                            }
                        }
                    }
                }
                binding.loginButton.isClickable = true
            }
        }
    }


}