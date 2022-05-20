package com.technoecorp.data.repository.auth.datasource

import com.technoecorp.domain.domainmodel.response.auth.login.LoginResponse
import com.technoecorp.domain.domainmodel.response.auth.otp.OtpResponse
import com.technoecorp.domain.domainmodel.response.auth.register.RegistrationResponse
import com.technoecorp.domain.domainmodel.request.DealerLoginRequest
import com.technoecorp.domain.domainmodel.request.DealerRegisterRequest
import com.technoecorp.domain.domainmodel.request.DealerValidationRequest
import retrofit2.Response

interface IAuthRemoteDatasource {
    suspend fun loginDealer(dealerLoginRequest: DealerLoginRequest): Response<LoginResponse>

    suspend fun registerDealer(dealerRegisterRequest: DealerRegisterRequest): Response<RegistrationResponse>

    suspend fun verifyDealer(dealerValidationRequest: DealerValidationRequest): Response<OtpResponse>
}