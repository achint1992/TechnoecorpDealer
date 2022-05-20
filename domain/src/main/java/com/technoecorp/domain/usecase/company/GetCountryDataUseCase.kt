package com.technoecorp.domain.usecase.company

import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.response.company.country.CountryResponse
import com.technoecorp.domain.repository.ICompanyDataRepository

class GetCountryDataUseCase(private val companyDataRepository: ICompanyDataRepository) {
    suspend fun execute(): ResultWrapper<CountryResponse> = companyDataRepository.getCountryData()
}