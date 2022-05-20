package com.technoecorp.domain.usecase.dealer

import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.UpdateProfileRequest
import com.technoecorp.domain.domainmodel.response.auth.otp.OtpResponse
import com.technoecorp.domain.repository.IDealerRepository

class UpdateDealerProfileUseCase(private var dealerRepository: IDealerRepository) {
    suspend fun execute(updateProfileRequest: UpdateProfileRequest): ResultWrapper<OtpResponse> =
        dealerRepository.updateDealerProfile(updateProfileRequest)
}