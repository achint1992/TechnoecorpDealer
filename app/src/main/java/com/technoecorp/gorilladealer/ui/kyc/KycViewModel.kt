package com.technoecorp.gorilladealer.ui.kyc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.data.repository.preference.datasource.PreferenceDatastore
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.data.Dealer
import com.technoecorp.domain.domainmodel.data.Kyc
import com.technoecorp.domain.domainmodel.response.dealer.kyc.KycResponse
import com.technoecorp.domain.usecase.dealer.UpdateKYCUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class KycViewModel(
    private var updateKYCUseCase: UpdateKYCUseCase,
    private var preferencesRepository: PreferencesRepository
) : ViewModel() {
    private var _updateKyc: MutableStateFlow<ResultWrapper<KycResponse>> =
        MutableStateFlow(ResultWrapper.Started())
    val updateKyc: StateFlow<ResultWrapper<KycResponse>> get() = _updateKyc

    fun uploadKycDetail(kyc: Kyc) {
        viewModelScope.launch {
            _updateKyc.value = ResultWrapper.Loading()
            try {
                val result = updateKYCUseCase.execute(kyc)
                _updateKyc.value = result
            } catch (e: Exception) {
                _updateKyc.value = ResultWrapper.Error(e.message)
            }
        }
    }

    fun saveData(dealer: Dealer) {
        viewModelScope.launch {
            preferencesRepository.saveObjectData(PreferenceDatastore.DEALER, dealer)
        }
    }
}