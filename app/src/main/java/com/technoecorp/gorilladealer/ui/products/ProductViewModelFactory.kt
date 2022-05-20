package com.technoecorp.gorilladealer.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.technoecorp.domain.usecase.company.CreatePaymentLinkUseCase
import com.technoecorp.domain.usecase.company.GetProductDataUseCase
import java.lang.IllegalArgumentException

class ProductViewModelFactory(
    private var getProductDataUseCase: GetProductDataUseCase,
    private var createPaymentLinkUseCase: CreatePaymentLinkUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java))
            return ProductViewModel(getProductDataUseCase, createPaymentLinkUseCase) as T
        throw IllegalArgumentException("Invalid View Model")
    }
}