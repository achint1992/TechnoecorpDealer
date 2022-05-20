package com.technoecorp.gorilladealer.di.component.subcomponent.otp

import com.technoecorp.gorilladealer.ui.otp.OtpFragment
import dagger.Subcomponent

@OtpScope
@Subcomponent(modules = [OtpViewModelFactoryModule::class])
interface OtpSubComponent {
    fun inject(otpFragment: OtpFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): OtpSubComponent
    }
}