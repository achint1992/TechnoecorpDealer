package com.technoecorp.gorilladealer.ui.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.data.repository.preference.datasource.PreferenceDatastore
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.data.Dealer
import com.technoecorp.domain.domainmodel.request.DealerValidationRequest
import com.technoecorp.domain.domainmodel.response.auth.otp.OtpResponse
import com.technoecorp.domain.usecase.auth.VerifyUserUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import timber.log.Timber

class OtpViewModel(
    private var verifyUserUseCase: VerifyUserUseCase,
    private var datastore: PreferencesRepository
) :
    ViewModel() {
    private var _otpResponse: MutableStateFlow<ResultWrapper<OtpResponse>> =
        MutableStateFlow(ResultWrapper.Started())
    val otpResponse: StateFlow<ResultWrapper<OtpResponse>> get() = _otpResponse

    fun verifyDealer(dealerValidationRequest: DealerValidationRequest) {
        viewModelScope.launch {
            _otpResponse.value = ResultWrapper.Loading()
            try {
                val otpData = verifyUserUseCase.execute(dealerValidationRequest)
                if (otpData is ResultWrapper.Success) {
                    if (otpData.data?.status!!) {
                        if (otpData.data != null) {
                            datastore.saveObjectData(
                                PreferenceDatastore.DEALER,
                                otpData.data?.data?.data?.dealer
                            )

                            Timber.e(otpData.data?.data?.data?.dealer.toString())

                            datastore.saveData(
                                PreferenceDatastore.DEALER_ID,
                                otpData.data?.data?.data?.dealer?.dealerId!!
                            )
                            datastore.saveData(
                                PreferenceDatastore.DISTRIBUTOR_ID,
                                otpData.data?.data?.data?.distributorId!!
                            )
                            datastore.saveData(PreferenceDatastore.LOGGED_IN, true)
                            datastore.saveData(PreferenceDatastore.VERIFIED, true)
                            datastore.saveData(PreferenceDatastore.COUNTRY_CODE, "+91")
                            _otpResponse.value = otpData
                        }

                    } else {
                        if (otpData.data?.message != null) {
                            _otpResponse.value = ResultWrapper.Error(otpData.data?.message)
                        } else {
                            _otpResponse.value = ResultWrapper.Error("Unknown error")
                        }
                    }
                } else {
                    _otpResponse.value = otpData
                }
            } catch (e: Exception) {
                _otpResponse.value = ResultWrapper.Error(e.message.toString())
            }
        }
    }

    suspend fun dealerData(): Dealer? {
        val data = viewModelScope.async {
            val dealer =
                datastore.getDataObject(PreferenceDatastore.DEALER, Dealer::class.java)
                    .take(1).first()
            Timber.e("verified == $dealer")

            dealer
        }.await()
        return data
    }

}