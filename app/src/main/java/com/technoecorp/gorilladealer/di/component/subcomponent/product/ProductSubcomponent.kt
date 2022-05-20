package com.technoecorp.gorilladealer.di.component.subcomponent.product

import com.technoecorp.gorilladealer.ui.products.ProductListFragment
import dagger.Subcomponent

@ProductScope
@Subcomponent(modules = [ProductViewModelFactoryModule::class])
interface ProductSubcomponent {
    fun inject(productListFragment: ProductListFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProductSubcomponent
    }
}