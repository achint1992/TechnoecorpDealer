package com.technoecorp.gorilladealer.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.data.Dealer
import com.technoecorp.domain.domainmodel.request.PaymentLinkRequest
import com.technoecorp.gorilladealer.R
import com.technoecorp.gorilladealer.databinding.FragmentProductListBinding
import com.technoecorp.gorilladealer.extensions.showShortToast
import com.technoecorp.gorilladealer.ui.TechnoecorpApplication
import com.technoecorp.gorilladealer.ui.custom.CustomDialogClass
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class ProductListFragment : Fragment() {
    @Inject
    lateinit var factory: ProductViewModelFactory
    private lateinit var viewModel: ProductViewModel
    private var _binding: FragmentProductListBinding? = null
    private val binding: FragmentProductListBinding get() = _binding!!
    private lateinit var productAdapter: ProductAdapter
    private lateinit var customDialogClass: CustomDialogClass
    private var _dealer: Dealer? = null
    private val dealer: Dealer get() = _dealer!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductListBinding.inflate(layoutInflater, container, false)
        (requireActivity().application as TechnoecorpApplication).getProductSubComponent()
            .inject(this)

        viewModel = ViewModelProvider(this, factory)[ProductViewModel::class.java]
        _dealer = (requireActivity().application as TechnoecorpApplication).getUpdateDealer()
        initViews()
        initCollector()
        viewModel.findProducts()
        return binding.root
    }

    private fun initViews() {
        customDialogClass = CustomDialogClass(requireContext())
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        productAdapter = ProductAdapter(requireContext(), ::getPaymentLink)
        binding.recyclerView.adapter = productAdapter
    }

    private fun getPaymentLink(productId: Int, packageId: Int, packagePrice: String) {
        viewModel.createPaymentLink(
            PaymentLinkRequest(
                productId,
                packageId,
                dealer.dealerId,
                1,
                packagePrice.toInt()
            )
        )
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
            viewModel.products.collectLatest { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        showDialog()
                    }
                    is ResultWrapper.Error -> {
                        dismissDialog()
                        requireContext().showShortToast(result.message)
                    }
                    is ResultWrapper.Success -> {
                        dismissDialog()
                        result.data?.status?.let {
                            if (it) {
                                productAdapter.submitList(result.data?.data)
                            } else {
                                requireContext().showShortToast(result.data?.statusCode.toString())
                            }
                        }
                    }
                    else -> {}
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.paymentLink.collectLatest { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        showDialog()
                    }
                    is ResultWrapper.Error -> {
                        dismissDialog()
                        requireContext().showShortToast(result.message)
                    }
                    is ResultWrapper.Success -> {
                        dismissDialog()
                        result.data?.status?.let {
                            if (it) {
                                val bundle = bundleOf(
                                    "url" to result.data?.data?.redirectUrl,
                                    "title" to "Please Wait.."
                                )
                                binding.root.findNavController()
                                    .navigate(
                                        R.id.action_productListFragment_to_webViewActivity,
                                        bundle
                                    )
                            } else {
                                requireContext().showShortToast(result.data?.message)
                            }
                        }
                    }
                    else -> {}
                }
            }
        }
    }

}