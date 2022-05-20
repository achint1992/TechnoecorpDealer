package com.technoecorp.data.di

import android.content.Context
import com.technoecorp.data.api.AuthService
import com.technoecorp.data.api.CompanyService
import com.technoecorp.data.api.DealerService
import com.technoecorp.data.repository.auth.datasource.AuthRemoteDatasource
import com.technoecorp.data.repository.auth.datasource.IAuthRemoteDatasource
import com.technoecorp.data.repository.company.datasource.CompanyRemoteDatasource
import com.technoecorp.data.repository.company.datasource.ICompanyRemoteDatasource
import com.technoecorp.data.repository.dealer.datasource.DealerRemoteDatasource
import com.technoecorp.data.repository.dealer.datasource.IDealerRemoteDatasource
import com.technoecorp.data.repository.preference.datasource.IPreferencesDatastore
import com.technoecorp.data.repository.preference.datasource.PreferenceDatastore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceModule {

    @Singleton
    @Provides
    fun providesDealerRemoteDataSource(dealerService: DealerService): IDealerRemoteDatasource {
        return DealerRemoteDatasource(dealerService)
    }

    @Singleton
    @Provides
    fun providesAuthRemoteDataSource(authService: AuthService): IAuthRemoteDatasource {
        return AuthRemoteDatasource(authService)
    }

    @Singleton
    @Provides
    fun providesCompanyRemoteDatasource(companyService: CompanyService): ICompanyRemoteDatasource {
        return CompanyRemoteDatasource(companyService)
    }

    @Singleton
    @Provides
    fun providesPreferenceDataStore(context: Context): IPreferencesDatastore {
        return PreferenceDatastore(context)
    }

}