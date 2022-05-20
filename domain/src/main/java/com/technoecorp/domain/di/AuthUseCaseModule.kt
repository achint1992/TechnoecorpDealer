package com.technoecorp.domain.di

import com.technoecorp.domain.repository.IAuthRepository
import com.technoecorp.domain.repository.IDealerRepository
import com.technoecorp.domain.usecase.auth.LoginDealerUseCase
import com.technoecorp.domain.usecase.auth.RegisterDealerUseCase
import com.technoecorp.domain.usecase.auth.VerifyUserUseCase
import dagger.Module
import dagger.Provides

@Module
class AuthUseCaseModule {

    @Provides
    fun providesLoginDealerUseCase(authRepository: IAuthRepository): LoginDealerUseCase {
        return LoginDealerUseCase(authRepository)
    }

    @Provides
    fun providesRegisterDealerUseCase(authRepository: IAuthRepository): RegisterDealerUseCase {
        return RegisterDealerUseCase(authRepository)
    }

    @Provides
    fun provideVerifyDealerUseCase(authRepository: IAuthRepository): VerifyUserUseCase {
        return VerifyUserUseCase(authRepository)
    }
}