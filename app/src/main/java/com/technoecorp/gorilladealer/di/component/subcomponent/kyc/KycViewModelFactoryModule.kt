package com.technoecorp.gorilladealer.di.component.subcomponent.kyc

import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.domain.usecase.dealer.UpdateKYCUseCase
import com.technoecorp.gorilladealer.ui.kyc.KycViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class KycViewModelFactoryModule {

    @KycScope
    @Provides
    fun providesKycViewModelFactory(
        updateKYCUseCase: UpdateKYCUseCase,
        preferencesRepository: PreferencesRepository
    ): KycViewModelFactory {
        return KycViewModelFactory(updateKYCUseCase, preferencesRepository)
    }
}