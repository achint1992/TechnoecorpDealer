package com.technoecorp.gorilladealer.di.component.subcomponent.dashboard

import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.domain.usecase.company.CreateWithdrawalUseCase
import com.technoecorp.domain.usecase.dealer.DealerAnalyticalDataUseCase
import com.technoecorp.domain.usecase.dealer.UpdateDealerProfilePicUseCase
import com.technoecorp.gorilladealer.ui.dashboard.DashboardViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class DashboardViewModelFactoryModule {

    @Provides
    @DashboardScope
    fun provideDashboardViewModelFactory(
        dealerAnalyticalDataUseCase: DealerAnalyticalDataUseCase,
        preferencesDatastore: PreferencesRepository,
        updateDealerProfilePicUseCase: UpdateDealerProfilePicUseCase,
        createWithdrawalUseCase: CreateWithdrawalUseCase
    ): DashboardViewModelFactory {
        return DashboardViewModelFactory(
            dealerAnalyticalDataUseCase,
            createWithdrawalUseCase,
            updateDealerProfilePicUseCase,
            preferencesDatastore
        )
    }
}