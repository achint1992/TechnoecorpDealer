package com.technoecorp.gorilladealer.di.component.subcomponent.mydealer

import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.domain.usecase.dealer.FilterDealerUseCase
import com.technoecorp.domain.usecase.dealer.GetCityWiseDealerUseCase
import com.technoecorp.domain.usecase.dealer.GetStateWiseDealerUseCase
import com.technoecorp.gorilladealer.ui.mydealer.MyDealerViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MyDealerViewModelFactoryModule {

    @MyDealerScope
    @Provides
    fun provideMyDealerViewModelFactory(
        getStateWiseDealerUseCase: GetStateWiseDealerUseCase,
        getCityWiseDealerUseCase: GetCityWiseDealerUseCase,
        filterDealerUseCase: FilterDealerUseCase,
        preferencesDatastore: PreferencesRepository
    ): MyDealerViewModelFactory {
        return MyDealerViewModelFactory(
            getStateWiseDealerUseCase,
            getCityWiseDealerUseCase,
            filterDealerUseCase,
            preferencesDatastore
        )
    }
}