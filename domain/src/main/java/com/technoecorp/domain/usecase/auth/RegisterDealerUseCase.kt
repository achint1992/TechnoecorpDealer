package com.technoecorp.domain.usecase.auth

import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.DealerRegisterRequest
import com.technoecorp.domain.domainmodel.response.auth.register.RegistrationResponse
import com.technoecorp.domain.repository.IAuthRepository

class RegisterDealerUseCase(private var authRepository: IAuthRepository) {
    suspend fun execute(dealerRegisterRequest: DealerRegisterRequest): ResultWrapper<RegistrationResponse> =
        authRepository.registerDealer(dealerRegisterRequest)
}