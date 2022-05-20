package com.technoecorp.gorilladealer.di.component.subcomponent.register

import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.domain.usecase.auth.LoginDealerUseCase
import com.technoecorp.domain.usecase.auth.RegisterDealerUseCase
import com.technoecorp.gorilladealer.ui.register.RegisterViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class RegisterViewModelModule {

    @RegisterScope
    @Provides
    fun providesRegisterViewModelFactory(
        loginDealerUseCase: LoginDealerUseCase,
        registerDealerUseCase: RegisterDealerUseCase,
        preferencesDatastore: PreferencesRepository
    ): RegisterViewModelFactory {
        return RegisterViewModelFactory(
            loginDealerUseCase,
            registerDealerUseCase,
            preferencesDatastore
        )
    }
}