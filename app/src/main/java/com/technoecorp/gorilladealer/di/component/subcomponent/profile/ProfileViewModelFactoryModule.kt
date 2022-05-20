package com.technoecorp.gorilladealer.di.component.subcomponent.profile

import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.domain.usecase.company.GetCityDataUseCase
import com.technoecorp.domain.usecase.company.GetCountryDataUseCase
import com.technoecorp.domain.usecase.company.GetStateDataUseCase
import com.technoecorp.domain.usecase.dealer.UpdateDealerProfileUseCase
import com.technoecorp.gorilladealer.ui.profile.ProfileViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ProfileViewModelFactoryModule {

    @Provides
    fun providesProfileViewModelFactory(
        updateDealerProfileUseCase: UpdateDealerProfileUseCase,
        getCountryDataUseCase: GetCountryDataUseCase,
        getStateDataUseCase: GetStateDataUseCase,
        getCityDataUseCase: GetCityDataUseCase,
        preferencesRepository: PreferencesRepository
    ): ProfileViewModelFactory {
        return ProfileViewModelFactory(
            updateDealerProfileUseCase,
            getCountryDataUseCase,
            getStateDataUseCase,
            getCityDataUseCase,
            preferencesRepository
        )
    }
}