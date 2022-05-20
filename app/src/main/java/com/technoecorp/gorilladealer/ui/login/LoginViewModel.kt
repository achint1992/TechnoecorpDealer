package com.technoecorp.gorilladealer.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.data.repository.preference.datasource.PreferenceDatastore
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.DealerLoginRequest
import com.technoecorp.domain.domainmodel.response.auth.login.LoginResponse
import com.technoecorp.domain.usecase.auth.LoginDealerUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

class LoginViewModel(
    private var loginDealerUseCase: LoginDealerUseCase,
    private var preferenceDatastore: PreferencesRepository
) : ViewModel() {

    private var _loginResponse: MutableStateFlow<ResultWrapper<LoginResponse>> =
        MutableStateFlow(ResultWrapper.Started())
    val loginResponse: StateFlow<ResultWrapper<LoginResponse>> = _loginResponse


    fun loginDealer(mobile: String, countryCode: String) = viewModelScope.launch {
        _loginResponse.value = ResultWrapper.Loading()
        try {
            val data = loginDealerUseCase.execute(DealerLoginRequest(mobile, countryCode))
            _loginResponse.value = data
        } catch (e: Exception) {
            _loginResponse.value = ResultWrapper.Error(e.message)
        }
    }

    suspend fun shouldNavigateToDashBoard(): Boolean {
        val data = viewModelScope.async {
            val loggedIn =
                preferenceDatastore.getData(PreferenceDatastore.LOGGED_IN, false).take(1).first()
            val verified =
                preferenceDatastore.getData(PreferenceDatastore.VERIFIED, false).take(1).first()

            loggedIn && verified
        }
        return data.await()
    }

}