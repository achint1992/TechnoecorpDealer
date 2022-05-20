package com.technoecorp.domain.usecase.dealer

import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.data.Dealer
import com.technoecorp.domain.domainmodel.request.CityWiseDealerCountRequest
import com.technoecorp.domain.domainmodel.response.auth.login.LoginResponse
import com.technoecorp.domain.domainmodel.response.dealer.citylist.CityWiseCountResponse
import com.technoecorp.domain.repository.IDealerRepository

class GetCityWiseDealerUseCase(private var dealerRepository: IDealerRepository) {
    suspend fun execute(cityWiseDealerCountRequest: CityWiseDealerCountRequest) : ResultWrapper<CityWiseCountResponse> =
        dealerRepository.getCityWiseDealer(cityWiseDealerCountRequest)

}