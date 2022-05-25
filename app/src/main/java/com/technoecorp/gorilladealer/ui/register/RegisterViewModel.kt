package com.technoecorp.gorilladealer.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.data.repository.preference.datasource.PreferenceDatastore
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.DealerLoginRequest
import com.technoecorp.domain.domainmodel.request.DealerRegisterRequest
import com.technoecorp.domain.domainmodel.response.auth.login.LoginResponse
import com.technoecorp.domain.usecase.auth.LoginDealerUseCase
import com.technoecorp.domain.usecase.auth.RegisterDealerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class RegisterViewModel(
    private var loginDealerUseCase: LoginDealerUseCase,
    private var registerDealerUseCase: RegisterDealerUseCase,
    private var preferencesDatastore: PreferencesRepository
) : ViewModel() {
    private var _registerResponse: MutableStateFlow<ResultWrapper<LoginResponse>> =
        MutableStateFlow(ResultWrapper.Started())
    val registerResponse: StateFlow<ResultWrapper<LoginResponse>> get() = _registerResponse

    fun register(dealerRegisterRequest: DealerRegisterRequest) = viewModelScope.launch {
        _registerResponse.value = ResultWrapper.Loading()
        try {

            when (val data = registerDealerUseCase.execute(dealerRegisterRequest)) {
                is ResultWrapper.Success -> {
                    if (data.data?.status!!) {
                        val login = loginDealerUseCase.execute(
                            DealerLoginRequest(
                                dealerRegisterRequest.mobileNo,
                                "+91"
                            )
                        )
                        _registerResponse.value = login
                    } else {
                        Timber.e("Data is${data.data.toString()}")
                        if (data.data?.message != null) {
                            _registerResponse.value = ResultWrapper.Error(data.data?.message)
                        } else {
                            _registerResponse.value = ResultWrapper.Error("Unknown Error")
                        }
                    }
                }
                else -> {
                    _registerResponse.value = ResultWrapper.Error(data.message)
                }
            }

        } catch (e: Exception) {
            _registerResponse.value = ResultWrapper.Error(e.message.toString())
        }
    }

    fun getDealerReferCode(callback: (String) -> Unit) {
        viewModelScope.launch {
            val refercode =
                preferencesDatastore.getData(PreferenceDatastore.REFER_CODE_DEEP_LINK, "")
                    .take(1).first()
            callback(refercode)
        }

    }

}