package com.technoecorp.domain.usecase.dealer

import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.data.Kyc
import com.technoecorp.domain.domainmodel.response.dealer.kyc.KycResponse
import com.technoecorp.domain.repository.IDealerRepository

class UpdateKYCUseCase(private var dealerRepository: IDealerRepository) {
    suspend fun execute(kyc: Kyc): ResultWrapper<KycResponse> = dealerRepository.updateKyc(kyc)
}