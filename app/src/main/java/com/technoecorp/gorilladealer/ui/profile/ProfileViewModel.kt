package com.technoecorp.gorilladealer.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.data.repository.preference.datasource.PreferenceDatastore
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.CityListRequest
import com.technoecorp.domain.domainmodel.request.StateListRequest
import com.technoecorp.domain.domainmodel.request.UpdateProfileRequest
import com.technoecorp.domain.domainmodel.response.auth.otp.Dealer
import com.technoecorp.domain.domainmodel.response.auth.otp.OtpResponse
import com.technoecorp.domain.domainmodel.response.company.city.CityResponse
import com.technoecorp.domain.domainmodel.response.company.country.CountryResponse
import com.technoecorp.domain.domainmodel.response.company.state.StateResponse
import com.technoecorp.domain.usecase.company.GetCityDataUseCase
import com.technoecorp.domain.usecase.company.GetCountryDataUseCase
import com.technoecorp.domain.usecase.company.GetStateDataUseCase
import com.technoecorp.domain.usecase.dealer.UpdateDealerProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class ProfileViewModel(
    private var updateDealerProfileUseCase: UpdateDealerProfileUseCase,
    private var getCountryDataUseCase: GetCountryDataUseCase,
    private var getStateDataUseCase: GetStateDataUseCase,
    private var getCityDataUseCase: GetCityDataUseCase,
    private var preferencesRepository: PreferencesRepository
) : ViewModel() {
    private var _updateProfile: MutableStateFlow<ResultWrapper<OtpResponse>> =
        MutableStateFlow(ResultWrapper.Started())
    val updateProfile: StateFlow<ResultWrapper<OtpResponse>> get() = _updateProfile

    private var _country: MutableStateFlow<ResultWrapper<CountryResponse>> =
        MutableStateFlow(ResultWrapper.Started())
    val country: StateFlow<ResultWrapper<CountryResponse>> get() = _country

    private var _state: MutableStateFlow<ResultWrapper<StateResponse>> =
        MutableStateFlow(ResultWrapper.Started())
    val stateList: StateFlow<ResultWrapper<StateResponse>> get() = _state

    private var _city: MutableStateFlow<ResultWrapper<CityResponse>> =
        MutableStateFlow(ResultWrapper.Started())
    val city: StateFlow<ResultWrapper<CityResponse>> get() = _city

    fun getCountry() {
        viewModelScope.launch {
            _country.value = ResultWrapper.Loading()
            try {
                val result = getCountryDataUseCase.execute()
                _country.value = result
            } catch (e: Exception) {
                _country.value = ResultWrapper.Error(e.message)
            }
        }
    }

    fun getState(countryId: Int) {
        Timber.e("country Id is $countryId")
        viewModelScope.launch {
            _state.value = ResultWrapper.Loading()
            try {
                val result = getStateDataUseCase.execute(StateListRequest(countryId))
                _state.value = result
            } catch (e: Exception) {
                _state.value = ResultWrapper.Error(e.message)
            }
        }
    }

    fun getCity(stateId: Int) {
        viewModelScope.launch {
            _city.value = ResultWrapper.Loading()
            try {
                val result = getCityDataUseCase.execute(CityListRequest(stateId))
                _city.value = result
            } catch (e: Exception) {
                _city.value = ResultWrapper.Error(e.message)
            }
        }
    }

    fun updateProfile(updateProfileRequest: UpdateProfileRequest) {
        viewModelScope.launch {
            _updateProfile.value = ResultWrapper.Loading()
            try {
                val result = updateDealerProfileUseCase.execute(updateProfileRequest)
                _updateProfile.value = result
            } catch (e: Exception) {
                _updateProfile.value = ResultWrapper.Error(e.message)
            }
        }
    }

    fun saveData(dealer: Dealer) {
        viewModelScope.launch {
            preferencesRepository.saveObjectData(PreferenceDatastore.DEALER, dealer)
        }
    }
}