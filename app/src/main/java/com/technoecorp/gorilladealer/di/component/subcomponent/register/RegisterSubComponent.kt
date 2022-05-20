package com.technoecorp.gorilladealer.di.component.subcomponent.register

import com.technoecorp.gorilladealer.ui.register.RegisterFragment
import dagger.Subcomponent

@RegisterScope
@Subcomponent(modules = [RegisterViewModelModule::class])
interface RegisterSubComponent {
    fun inject(registerFragment: RegisterFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): RegisterSubComponent
    }
}