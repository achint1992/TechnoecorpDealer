package com.technoecorp.domain.usecase.dealer

import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.DealerAnalyticalRequest
import com.technoecorp.domain.domainmodel.response.dealer.dashboard.DashboardAnayliticsResponse
import com.technoecorp.domain.repository.IDealerRepository

class DealerAnalyticalDataUseCase(private var dealerRepository: IDealerRepository) {
    suspend fun execute(dealerAnalyticalRequest: DealerAnalyticalRequest): ResultWrapper<DashboardAnayliticsResponse> =
        dealerRepository.getDealerAnalyticalData(dealerAnalyticalRequest)
}