package com.technoecorp.domain.di

import com.technoecorp.domain.repository.ICompanyDataRepository
import com.technoecorp.domain.usecase.company.*
import dagger.Module
import dagger.Provides

@Module
class CompanyUseCaseModule {

    @Provides
    fun providesCreatePaymentLinkUseCase(companyDataRepository: ICompanyDataRepository): CreatePaymentLinkUseCase {
        return CreatePaymentLinkUseCase(companyDataRepository)
    }

    @Provides
    fun provideCreateWithdrawalUseCase(companyDataRepository: ICompanyDataRepository): CreateWithdrawalUseCase {
        return CreateWithdrawalUseCase(companyDataRepository)
    }

    @Provides
    fun providesGetCityDataUseCase(companyDataRepository: ICompanyDataRepository): GetCityDataUseCase {
        return GetCityDataUseCase(companyDataRepository)
    }

    @Provides
    fun providesCountryDataUseCase(companyDataRepository: ICompanyDataRepository): GetCountryDataUseCase {
        return GetCountryDataUseCase(companyDataRepository)
    }

    @Provides
    fun providesGalleryDataUseCase(companyDataRepository: ICompanyDataRepository): GetGalleryDataUseCase {
        return GetGalleryDataUseCase(companyDataRepository)
    }

    @Provides
    fun providesGetProductDataUseCase(companyDataRepository: ICompanyDataRepository): GetProductDataUseCase {
        return GetProductDataUseCase(companyDataRepository)
    }

    @Provides
    fun provideGetStateDataUseCase(companyDataRepository: ICompanyDataRepository): GetStateDataUseCase {
        return GetStateDataUseCase(companyDataRepository)
    }
}