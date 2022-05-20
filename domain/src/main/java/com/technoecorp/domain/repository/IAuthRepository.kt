package com.technoecorp.domain.repository

import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.DealerLoginRequest
import com.technoecorp.domain.domainmodel.request.DealerRegisterRequest
import com.technoecorp.domain.domainmodel.request.DealerValidationRequest
import com.technoecorp.domain.domainmodel.response.auth.login.LoginResponse
import com.technoecorp.domain.domainmodel.response.auth.otp.OtpResponse
import com.technoecorp.domain.domainmodel.response.auth.register.RegistrationResponse
import retrofit2.Response

interface IAuthRepository {

    suspend fun loginDealer(dealerLoginRequest: DealerLoginRequest): ResultWrapper<LoginResponse>

    suspend fun registerDealer(dealerRegisterRequest: DealerRegisterRequest): ResultWrapper<RegistrationResponse>

    suspend fun verifyDealer(dealerValidationRequest: DealerValidationRequest): ResultWrapper<OtpResponse>

}