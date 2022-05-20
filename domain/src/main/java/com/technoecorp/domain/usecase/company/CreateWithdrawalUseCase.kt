package com.technoecorp.domain.usecase.company

import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.GalleryDataRequest
import com.technoecorp.domain.domainmodel.request.WithdrawalMoneyRequest
import com.technoecorp.domain.domainmodel.response.BaseResponse
import com.technoecorp.domain.domainmodel.response.auth.login.LoginResponse
import com.technoecorp.domain.repository.ICompanyDataRepository

class CreateWithdrawalUseCase(private val companyDataRepository: ICompanyDataRepository) {
    suspend fun execute(withdrawalMoneyRequest: WithdrawalMoneyRequest): ResultWrapper<BaseResponse> =
        companyDataRepository.createWithdrawalRequest(withdrawalMoneyRequest)
}