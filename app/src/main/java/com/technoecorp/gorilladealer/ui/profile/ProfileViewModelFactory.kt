package com.technoecorp.gorilladealer.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.domain.usecase.company.GetCityDataUseCase
import com.technoecorp.domain.usecase.company.GetCountryDataUseCase
import com.technoecorp.domain.usecase.company.GetStateDataUseCase
import com.technoecorp.domain.usecase.dealer.UpdateDealerProfileUseCase
import java.lang.IllegalArgumentException

class ProfileViewModelFactory(
    private var updateDealerProfileUseCase: UpdateDealerProfileUseCase,
    private var getCountryDataUseCase: GetCountryDataUseCase,
    private var getStateDataUseCase: GetStateDataUseCase,
    private var getCityDataUseCase: GetCityDataUseCase,
    private var preferencesRepository: PreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(
                updateDealerProfileUseCase,
                getCountryDataUseCase,
                getStateDataUseCase,
                getCityDataUseCase,
                preferencesRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}