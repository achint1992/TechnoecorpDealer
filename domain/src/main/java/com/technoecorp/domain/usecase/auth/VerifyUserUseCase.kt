package com.technoecorp.domain.usecase.auth

import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.DealerValidationRequest
import com.technoecorp.domain.domainmodel.response.auth.otp.OtpResponse
import com.technoecorp.domain.repository.IAuthRepository

class VerifyUserUseCase(private var authRepository: IAuthRepository) {
    suspend fun execute(dealerValidationRequest: DealerValidationRequest): ResultWrapper<OtpResponse> =
        authRepository.verifyDealer(dealerValidationRequest)
}