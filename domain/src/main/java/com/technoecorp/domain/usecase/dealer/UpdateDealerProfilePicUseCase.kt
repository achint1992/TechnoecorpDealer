package com.technoecorp.domain.usecase.dealer

import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.UpdateProfilePicRequest
import com.technoecorp.domain.domainmodel.request.UpdateProfileRequest
import com.technoecorp.domain.domainmodel.response.auth.otp.OtpResponse
import com.technoecorp.domain.domainmodel.response.dealer.citylist.CityWiseCountResponse
import com.technoecorp.domain.repository.IDealerRepository

class UpdateDealerProfilePicUseCase(private var dealerRepository: IDealerRepository) {
    suspend fun execute(updateProfileRequest: UpdateProfilePicRequest): ResultWrapper<OtpResponse> =
        dealerRepository.updateDealerProfilePic(updateProfileRequest)
}