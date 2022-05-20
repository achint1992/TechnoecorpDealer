package com.technoecorp.domain.di

import com.technoecorp.domain.repository.IDealerRepository
import com.technoecorp.domain.usecase.dealer.*
import dagger.Module
import dagger.Provides

@Module
class DealerUseCaseModule {

    @Provides
    fun providesDealerAnalyticalDataUseCase(dealerRepository: IDealerRepository): DealerAnalyticalDataUseCase {
        return DealerAnalyticalDataUseCase(dealerRepository)
    }

    @Provides
    fun providesFilterDealerUseCase(dealerRepository: IDealerRepository): FilterDealerUseCase {
        return FilterDealerUseCase(dealerRepository)
    }

    @Provides
    fun providesGetCityWiseDealerUseCase(dealerRepository: IDealerRepository): GetCityWiseDealerUseCase {
        return GetCityWiseDealerUseCase(dealerRepository)
    }

    @Provides
    fun providesGetDealerProfileUseCase(dealerRepository: IDealerRepository): GetDealerProfileUseCase {
        return GetDealerProfileUseCase(dealerRepository)
    }

    @Provides
    fun providesGetStateWiseDealerUseCase(dealerRepository: IDealerRepository): GetStateWiseDealerUseCase {
        return GetStateWiseDealerUseCase(dealerRepository)
    }

    @Provides
    fun providesUpdateDealerProfileUseCase(dealerRepository: IDealerRepository): UpdateDealerProfileUseCase {
        return UpdateDealerProfileUseCase(dealerRepository)
    }

    @Provides
    fun providesUpdateDealerProfilePicUseCase(dealerRepository: IDealerRepository): UpdateDealerProfilePicUseCase {
        return UpdateDealerProfilePicUseCase(dealerRepository)
    }

    @Provides
    fun provideUpdateKycUseCase(dealerRepository: IDealerRepository): UpdateKYCUseCase {
        return UpdateKYCUseCase(dealerRepository)
    }

}