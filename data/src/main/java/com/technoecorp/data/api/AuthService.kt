package com.technoecorp.data.api

import com.technoecorp.domain.domainmodel.request.DealerLoginRequest
import com.technoecorp.domain.domainmodel.request.DealerRegisterRequest
import com.technoecorp.domain.domainmodel.request.DealerValidationRequest
import com.technoecorp.domain.domainmodel.response.auth.login.LoginResponse
import com.technoecorp.domain.domainmodel.response.auth.otp.OtpResponse
import com.technoecorp.domain.domainmodel.response.auth.register.RegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun loginDealer(@Body dealerLoginRequest: DealerLoginRequest): Response<LoginResponse>

    @POST("auth/verifyOtp")
    suspend fun verifyOtp(@Body dealerValidationRequest: DealerValidationRequest): Response<OtpResponse>

    @POST("auth/signupDealer")
    suspend fun registerDealer(@Body dealerRegisterRequest: DealerRegisterRequest): Response<RegistrationResponse>
}