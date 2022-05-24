package com.technoecorp.gorilladealer.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.DealerLoginRequest
import com.technoecorp.domain.domainmodel.response.auth.login.LoginResponse
import com.technoecorp.domain.usecase.auth.LoginDealerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private var loginDealerUseCase: LoginDealerUseCase
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


}