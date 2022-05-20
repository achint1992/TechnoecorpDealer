package com.technoecorp.gorilladealer.di.component.subcomponent.login

import com.technoecorp.gorilladealer.ui.login.LoginFragment
import dagger.Subcomponent

@LoginScope
@Subcomponent(modules = [LoginViewModelFactoryModule::class])
interface LoginSubComponent {

    fun inject(loginFragment: LoginFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginSubComponent
    }
}