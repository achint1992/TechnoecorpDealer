package com.technoecorp.domain.usecase.company

import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.CityListRequest
import com.technoecorp.domain.domainmodel.response.company.city.CityResponse
import com.technoecorp.domain.repository.ICompanyDataRepository

class GetCityDataUseCase(private val companyDataRepository: ICompanyDataRepository) {
    suspend fun execute(cityListRequest: CityListRequest): ResultWrapper<CityResponse> =
        companyDataRepository.getCityData(cityListRequest)
}