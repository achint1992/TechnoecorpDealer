package com.technoecorp.gorilladealer.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.data.repository.preference.datasource.PreferenceDatastore
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.DealerAnalyticalRequest
import com.technoecorp.domain.domainmodel.request.UpdateProfilePicRequest
import com.technoecorp.domain.domainmodel.request.WithdrawalMoneyRequest
import com.technoecorp.domain.domainmodel.response.BaseResponse
import com.technoecorp.domain.domainmodel.response.auth.otp.Dealer
import com.technoecorp.domain.domainmodel.response.auth.otp.OtpResponse
import com.technoecorp.domain.domainmodel.response.dealer.dashboard.DashboardAnayliticsResponse
import com.technoecorp.domain.usecase.company.CreateWithdrawalUseCase
import com.technoecorp.domain.usecase.dealer.DealerAnalyticalDataUseCase
import com.technoecorp.domain.usecase.dealer.UpdateDealerProfilePicUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

class DashboardViewModel(
    private var dealerAnalyticalDataUseCase: DealerAnalyticalDataUseCase,
    private var createWithdrawalUseCase: CreateWithdrawalUseCase,
    private var updateDealerProfilePicUseCase: UpdateDealerProfilePicUseCase,
    private var preferencesDatastore: PreferencesRepository
) : ViewModel() {
    private var _dashboardAnalysis: MutableStateFlow<ResultWrapper<DashboardAnayliticsResponse>> =
        MutableStateFlow(ResultWrapper.Started())
    val dashboardAnalytics: StateFlow<ResultWrapper<DashboardAnayliticsResponse>> get() = _dashboardAnalysis

    private var _withDrawlResponse: MutableStateFlow<ResultWrapper<BaseResponse>> =
        MutableStateFlow(ResultWrapper.Started())
    val withDrawlResponse: StateFlow<ResultWrapper<BaseResponse>> get() = _withDrawlResponse

    private var _updateProfilePic: MutableStateFlow<ResultWrapper<OtpResponse>> =
        MutableStateFlow(ResultWrapper.Started())
    val updateProfilePic: StateFlow<ResultWrapper<OtpResponse>> get() = _updateProfilePic

    fun createListForDashboardData(dealerAnalyticalRequest: DealerAnalyticalRequest) {
        viewModelScope.launch {
            _dashboardAnalysis.value = ResultWrapper.Loading()
            try {
                val analysis = dealerAnalyticalDataUseCase.execute(dealerAnalyticalRequest)
                _dashboardAnalysis.value = analysis

            } catch (e: Exception) {
                _dashboardAnalysis.value = ResultWrapper.Error(e.message)
            }
        }
    }

    fun createWithdrawalRequest(withdrawalMoneyRequest: WithdrawalMoneyRequest) {
        viewModelScope.launch {
            _withDrawlResponse.value = ResultWrapper.Loading()
            try {
                val response = createWithdrawalUseCase.execute(withdrawalMoneyRequest)
                _withDrawlResponse.value = response
            } catch (e: Exception) {
                _withDrawlResponse.value = ResultWrapper.Error(e.message)
            }
        }
    }

    fun updateProfilePic(updateProfilePicRequest: UpdateProfilePicRequest) {
        viewModelScope.launch {
            _updateProfilePic.value = ResultWrapper.Loading()
            try {
                val response = updateDealerProfilePicUseCase.execute(updateProfilePicRequest)
                _updateProfilePic.value = response
            } catch (e: Exception) {
                _updateProfilePic.value = ResultWrapper.Error(e.message)
            }
        }
    }

    fun saveData(dealer: Dealer) {
        viewModelScope.launch {
            preferencesDatastore.saveObjectData(PreferenceDatastore.DEALER, dealer)
        }
    }

    fun saveDashboardResponse(data: DashboardAnayliticsResponse) {
        viewModelScope.launch {
            preferencesDatastore.saveObjectData(PreferenceDatastore.DASHBOARD_RESPONSE, data)
        }
    }

    fun saveRecentUsers(recentDealers: List<String>) {
        viewModelScope.launch {
            preferencesDatastore.saveObjectData(PreferenceDatastore.RECENT_LIST, recentDealers)
        }
    }

    fun getRecentData(callback: (ArrayList<String>) -> Unit) {
        viewModelScope.launch {
            val recentData = preferencesDatastore.getArrayObject<String>(
                PreferenceDatastore.RECENT_LIST
            ).take(1).first()
            callback(recentData)
        }
    }


}