package com.technoecorp.domain.usecase.dealer

import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.StateWiseDealerCountRequest
import com.technoecorp.domain.domainmodel.response.dealer.citylist.CityWiseCountResponse
import com.technoecorp.domain.domainmodel.response.dealer.statelist.StateWiseCountResponse
import com.technoecorp.domain.repository.IDealerRepository

class GetStateWiseDealerUseCase(private var dealerRepository: IDealerRepository) {
    suspend fun execute(stateWiseDealerCountRequest: StateWiseDealerCountRequest): ResultWrapper<StateWiseCountResponse> =
        dealerRepository.getStateWiseDealer(stateWiseDealerCountRequest)
}