package com.technoecorp.gorilladealer.di.component.subcomponent.login

import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.domain.usecase.auth.LoginDealerUseCase
import com.technoecorp.gorilladealer.ui.login.LoginViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class LoginViewModelFactoryModule {

    @LoginScope
    @Provides
    fun providesLiveViewModelFactory(
        loginDealerUseCase: LoginDealerUseCase,
        preferenceDatastore: PreferencesRepository
    ): LoginViewModelFactory {
        return LoginViewModelFactory(loginDealerUseCase, preferenceDatastore)
    }

}