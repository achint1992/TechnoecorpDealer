package com.technoecorp.gorilladealer.di.component.subcomponent.product

import com.technoecorp.domain.usecase.company.CreatePaymentLinkUseCase
import com.technoecorp.domain.usecase.company.GetProductDataUseCase
import com.technoecorp.gorilladealer.ui.products.ProductViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ProductViewModelFactoryModule {

    @ProductScope
    @Provides
    fun providesProductViewModelFactory(
        getProductDataUseCase: GetProductDataUseCase,
        createPaymentLinkUseCase: CreatePaymentLinkUseCase
    ): ProductViewModelFactory {
        return ProductViewModelFactory(getProductDataUseCase, createPaymentLinkUseCase)
    }
}