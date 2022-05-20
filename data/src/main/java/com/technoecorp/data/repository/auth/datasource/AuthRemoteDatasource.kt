package com.technoecorp.data.repository.auth.datasource

import com.technoecorp.data.api.AuthService
import com.technoecorp.domain.domainmodel.response.auth.login.LoginResponse
import com.technoecorp.domain.domainmodel.response.auth.otp.OtpResponse
import com.technoecorp.domain.domainmodel.response.auth.register.RegistrationResponse
import com.technoecorp.domain.domainmodel.request.DealerLoginRequest
import com.technoecorp.domain.domainmodel.request.DealerRegisterRequest
import com.technoecorp.domain.domainmodel.request.DealerValidationRequest
import retrofit2.Response

class AuthRemoteDatasource(private var authService: AuthService) : IAuthRemoteDatasource {
    override suspend fun loginDealer(dealerLoginRequest: DealerLoginRequest): Response<LoginResponse> {
        return authService.loginDealer(dealerLoginRequest)
    }

    override suspend fun registerDealer(dealerRegisterRequest: DealerRegisterRequest): Response<RegistrationResponse> {
        return authService.registerDealer(dealerRegisterRequest)
    }

    override suspend fun verifyDealer(dealerValidationRequest: DealerValidationRequest): Response<OtpResponse> {
        return authService.verifyOtp(dealerValidationRequest)
    }


}