package com.technoecorp.domain.usecase.company

import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.StateListRequest
import com.technoecorp.domain.domainmodel.response.auth.login.LoginResponse
import com.technoecorp.domain.domainmodel.response.company.state.StateResponse
import com.technoecorp.domain.repository.ICompanyDataRepository

class GetStateDataUseCase(private val companyDataRepository: ICompanyDataRepository) {
    suspend fun execute(stateListRequest: StateListRequest): ResultWrapper<StateResponse> =
        companyDataRepository.getStateData(stateListRequest)
}