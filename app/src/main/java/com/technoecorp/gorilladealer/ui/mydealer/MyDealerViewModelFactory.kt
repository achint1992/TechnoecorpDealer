package com.technoecorp.gorilladealer.ui.mydealer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.domain.usecase.dealer.FilterDealerUseCase
import com.technoecorp.domain.usecase.dealer.GetCityWiseDealerUseCase
import com.technoecorp.domain.usecase.dealer.GetStateWiseDealerUseCase

class MyDealerViewModelFactory(
    private var getStateWiseDealerUseCase: GetStateWiseDealerUseCase,
    private var getCityWiseDealerUseCase: GetCityWiseDealerUseCase,
    private var filterDealerUseCase: FilterDealerUseCase,
    private var preferencesDatastore: PreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyDealerViewModel::class.java)) {
            return MyDealerViewModel(
                getStateWiseDealerUseCase,
                getCityWiseDealerUseCase,
                filterDealerUseCase,
                preferencesDatastore
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}