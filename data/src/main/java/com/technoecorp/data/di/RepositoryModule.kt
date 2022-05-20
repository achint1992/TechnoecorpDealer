package com.technoecorp.data.di

import com.technoecorp.data.repository.auth.AuthRepository
import com.technoecorp.data.repository.auth.datasource.IAuthRemoteDatasource
import com.technoecorp.data.repository.company.CompanyDataRepository
import com.technoecorp.data.repository.company.datasource.ICompanyRemoteDatasource
import com.technoecorp.data.repository.dealer.DealerRepository
import com.technoecorp.data.repository.dealer.datasource.IDealerRemoteDatasource
import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.data.repository.preference.datasource.IPreferencesDatastore
import com.technoecorp.domain.repository.IAuthRepository
import com.technoecorp.domain.repository.ICompanyDataRepository
import com.technoecorp.domain.repository.IDealerRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun providesAuthRepository(authRemoteDatasource: IAuthRemoteDatasource): IAuthRepository {
        return AuthRepository(authRemoteDatasource)
    }

    @Singleton
    @Provides
    fun providesDealerRepository(dealerRemoteDatasource: IDealerRemoteDatasource): IDealerRepository {
        return DealerRepository(dealerRemoteDatasource)
    }

    @Singleton
    @Provides
    fun providesCompanyRepository(companyRemoteDatasource: ICompanyRemoteDatasource): ICompanyDataRepository {
        return CompanyDataRepository(companyRemoteDatasource)
    }

    @Singleton
    @Provides
    fun providesPreferencesRepository(preferenceDatastore: IPreferencesDatastore): PreferencesRepository {
        return PreferencesRepository(preferenceDatastore)
    }

}