package com.technoecorp.domain.usecase.auth

import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.DealerLoginRequest
import com.technoecorp.domain.domainmodel.response.auth.login.LoginResponse
import com.technoecorp.domain.repository.IAuthRepository

class LoginDealerUseCase(private var authRepository: IAuthRepository) {
    suspend fun execute(dealerLoginRequest: DealerLoginRequest):ResultWrapper<LoginResponse> =
        authRepository.loginDealer(dealerLoginRequest)

}