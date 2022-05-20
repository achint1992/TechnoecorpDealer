package com.technoecorp.gorilladealer.di.component.subcomponent.otp

import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.domain.usecase.auth.VerifyUserUseCase
import com.technoecorp.gorilladealer.ui.otp.OtpViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class OtpViewModelFactoryModule {

    @OtpScope
    @Provides
    fun provideOtpViewModelFactory(
        verifyUserUseCase: VerifyUserUseCase,
        preferenceDatastore: PreferencesRepository
    ): OtpViewModelFactory {
        return OtpViewModelFactory(verifyUserUseCase, preferenceDatastore)
    }
}