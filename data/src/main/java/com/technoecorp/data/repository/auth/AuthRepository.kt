package com.technoecorp.data.repository.auth

import com.technoecorp.data.repository.auth.datasource.IAuthRemoteDatasource
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.ResultWrapperConvertor
import com.technoecorp.domain.domainmodel.request.DealerLoginRequest
import com.technoecorp.domain.domainmodel.request.DealerRegisterRequest
import com.technoecorp.domain.domainmodel.request.DealerValidationRequest
import com.technoecorp.domain.domainmodel.response.auth.login.LoginResponse
import com.technoecorp.domain.domainmodel.response.auth.otp.OtpResponse
import com.technoecorp.domain.domainmodel.response.auth.register.RegistrationResponse
import com.technoecorp.domain.repository.IAuthRepository
import retrofit2.Response

class AuthRepository(var authRemoteDataSource: IAuthRemoteDatasource) : IAuthRepository {


    override suspend fun loginDealer(dealerLoginRequest: DealerLoginRequest): ResultWrapper<LoginResponse> {
        return ResultWrapperConvertor.responseToResource(
            authRemoteDataSource.loginDealer(
                dealerLoginRequest
            )
        )
    }

    override suspend fun registerDealer(dealerRegisterRequest: DealerRegisterRequest): ResultWrapper<RegistrationResponse> {
        return ResultWrapperConvertor.responseToResource(
            authRemoteDataSource.registerDealer(
                dealerRegisterRequest
            )
        )
    }

    override suspend fun verifyDealer(dealerValidationRequest: DealerValidationRequest): ResultWrapper<OtpResponse> {
        return ResultWrapperConvertor.responseToResource(
            authRemoteDataSource.verifyDealer(
                dealerValidationRequest
            )
        )
    }

}