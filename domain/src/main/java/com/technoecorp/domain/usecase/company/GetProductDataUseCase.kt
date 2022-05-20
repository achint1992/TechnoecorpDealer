package com.technoecorp.domain.usecase.company

import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.response.company.product.ProductListResponse
import com.technoecorp.domain.repository.ICompanyDataRepository

class GetProductDataUseCase(private val companyDataRepository: ICompanyDataRepository) {
    suspend fun execute(): ResultWrapper<ProductListResponse> = companyDataRepository.getProductData()
}