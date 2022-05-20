package com.technoecorp.gorilladealer.di.component.subcomponent.kyc

import com.technoecorp.gorilladealer.ui.kyc.KycFragment
import dagger.Subcomponent

@KycScope
@Subcomponent(modules = [KycViewModelFactoryModule::class])
interface KycSubcomponent {
    fun inject(kycFragment: KycFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): KycSubcomponent
    }
}