package com.technoecorp.gorilladealer.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.PaymentLinkRequest
import com.technoecorp.domain.domainmodel.response.company.payment.PaymentLinkResponse
import com.technoecorp.domain.domainmodel.response.company.product.ProductListResponse
import com.technoecorp.domain.usecase.company.CreatePaymentLinkUseCase
import com.technoecorp.domain.usecase.company.GetProductDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class ProductViewModel(
    private var getProductDataUseCase: GetProductDataUseCase,
    private var createPaymentLinkUseCase: CreatePaymentLinkUseCase
) : ViewModel() {
    private var _products: MutableStateFlow<ResultWrapper<ProductListResponse>> =
        MutableStateFlow(ResultWrapper.Loading())
    val products: StateFlow<ResultWrapper<ProductListResponse>> get() = _products

    private var _paymentLink: MutableStateFlow<ResultWrapper<PaymentLinkResponse>> =
        MutableStateFlow(ResultWrapper.Loading())
    val paymentLink: StateFlow<ResultWrapper<PaymentLinkResponse>> get() = _paymentLink

    fun findProducts() {
        viewModelScope.launch {
            _products.value = ResultWrapper.Loading()
            try {
                val response = getProductDataUseCase.execute()
                _products.value = response
            } catch (e: Exception) {
                _products.value = ResultWrapper.Error(e.message)
            }
        }
    }

    fun createPaymentLink(paymentLinkRequest: PaymentLinkRequest) {
        viewModelScope.launch {
            _paymentLink.value = ResultWrapper.Loading()
            try {
                val response = createPaymentLinkUseCase.execute(paymentLinkRequest)
                _paymentLink.value = response
            } catch (e: Exception) {
                _paymentLink.value = ResultWrapper.Error(e.message)
            }
        }
    }
}