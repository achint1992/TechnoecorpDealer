package com.technoecorp.gorilladealer.ui.mydealer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.data.repository.preference.datasource.PreferenceDatastore
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.CityWiseDealerCountRequest
import com.technoecorp.domain.domainmodel.request.DealerFilterRequest
import com.technoecorp.domain.domainmodel.request.StateWiseDealerCountRequest
import com.technoecorp.domain.domainmodel.response.dealer.citylist.CityWiseCountResponse
import com.technoecorp.domain.domainmodel.response.dealer.dashboard.DashboardAnayliticsResponse
import com.technoecorp.domain.domainmodel.response.dealer.filter.DealerFilterResponse
import com.technoecorp.domain.domainmodel.response.dealer.statelist.StateWiseCountResponse
import com.technoecorp.domain.usecase.dealer.FilterDealerUseCase
import com.technoecorp.domain.usecase.dealer.GetCityWiseDealerUseCase
import com.technoecorp.domain.usecase.dealer.GetStateWiseDealerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyDealerViewModel(
    private var getStateWiseDealerUseCase: GetStateWiseDealerUseCase,
    private var getCityWiseDealerUseCase: GetCityWiseDealerUseCase,
    private var filterDealerUseCase: FilterDealerUseCase,
    private var preferencesDatastore: PreferencesRepository
) : ViewModel() {
    //to observe state by count for dealers
    private var _stateList: MutableStateFlow<ResultWrapper<StateWiseCountResponse>> =
        MutableStateFlow(ResultWrapper.Started())
    val stateList: StateFlow<ResultWrapper<StateWiseCountResponse>> get() = _stateList

    //to observer city by count for dealers
    private var _cityList: MutableStateFlow<ResultWrapper<CityWiseCountResponse>> =
        MutableStateFlow(ResultWrapper.Started())
    val cityList: StateFlow<ResultWrapper<CityWiseCountResponse>> get() = _cityList


    fun getStateWiseCount(stateWiseDealerCountRequest: StateWiseDealerCountRequest) {
        viewModelScope.launch {
            _stateList.value = ResultWrapper.Loading()
            try {
                val response = getStateWiseDealerUseCase.execute(stateWiseDealerCountRequest)
                _stateList.value = response
            } catch (e: Exception) {
                _stateList.value = ResultWrapper.Error(e.message)
            }
        }
    }

    fun getCityWiseCount(cityWiseDealerCountRequest: CityWiseDealerCountRequest) {
        viewModelScope.launch {
            _cityList.value = ResultWrapper.Loading()
            try {
                val response = getCityWiseDealerUseCase.execute(cityWiseDealerCountRequest)
                _cityList.value = response
            } catch (e: Exception) {
                _cityList.value = ResultWrapper.Error(e.message)
            }
        }
    }

    suspend fun getDealerList(dealerFilterRequest: DealerFilterRequest): ResultWrapper<DealerFilterResponse> {
        val data = withContext(viewModelScope.coroutineContext) {
            val response = filterDealerUseCase.execute(dealerFilterRequest)
            response
        }
        return data
    }


    suspend fun getLastDashboardData(): DashboardAnayliticsResponse? {
        val data = withContext(viewModelScope.coroutineContext) {
            val recentDashboard =
                preferencesDatastore.getDataObject(PreferenceDatastore.DASHBOARD_RESPONSE,DashboardAnayliticsResponse::class.java)
                    .take(1).first()
            recentDashboard
        }
        return data
    }


}