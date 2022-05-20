package com.technoecorp.gorilladealer.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.domain.usecase.company.CreateWithdrawalUseCase
import com.technoecorp.domain.usecase.dealer.DealerAnalyticalDataUseCase
import com.technoecorp.domain.usecase.dealer.UpdateDealerProfilePicUseCase

class DashboardViewModelFactory(
    private var dealerAnalyticalDataUseCase: DealerAnalyticalDataUseCase,
    private var createWithdrawalUseCase: CreateWithdrawalUseCase,
    private var updateDealerProfilePicUseCase: UpdateDealerProfilePicUseCase,
    private var preferencesDatastore: PreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(
                dealerAnalyticalDataUseCase,
                createWithdrawalUseCase,
                updateDealerProfilePicUseCase,
                preferencesDatastore
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}