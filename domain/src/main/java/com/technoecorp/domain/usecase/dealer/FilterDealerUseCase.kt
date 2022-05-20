package com.technoecorp.domain.usecase.dealer

import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.DealerFilterRequest
import com.technoecorp.domain.domainmodel.response.dealer.filter.DealerFilterResponse
import com.technoecorp.domain.repository.IDealerRepository

class FilterDealerUseCase(private var dealerRepository: IDealerRepository) {
    suspend fun execute(dealerFilterRequest: DealerFilterRequest): ResultWrapper<DealerFilterResponse> =
        dealerRepository.getFilteredDealerList(dealerFilterRequest)
}