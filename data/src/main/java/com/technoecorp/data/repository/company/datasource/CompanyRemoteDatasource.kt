package com.technoecorp.data.repository.company.datasource

import com.technoecorp.data.api.CompanyService
import com.technoecorp.domain.domainmodel.request.*
import com.technoecorp.domain.domainmodel.response.BaseResponse
import com.technoecorp.domain.domainmodel.response.company.city.CityResponse
import com.technoecorp.domain.domainmodel.response.company.country.CountryResponse
import com.technoecorp.domain.domainmodel.response.company.gallery.GalleryResponse
import com.technoecorp.domain.domainmodel.response.company.payment.PaymentLinkResponse
import com.technoecorp.domain.domainmodel.response.company.product.ProductListResponse
import com.technoecorp.domain.domainmodel.response.company.state.StateResponse
import retrofit2.Response

class CompanyRemoteDatasource(private var companyService: CompanyService) :
    ICompanyRemoteDatasource {
    override suspend fun getAllCountryList(): Response<CountryResponse> {
        return companyService.getAllCountryList()
    }

    override suspend fun getAllStateByCountry(stateListRequest: StateListRequest): Response<StateResponse> {
        return companyService.getAllStateByCountry(stateListRequest)
    }

    override suspend fun getALlCityByState(cityListRequest: CityListRequest): Response<CityResponse> {
        return companyService.getAllCityByState(cityListRequest)
    }

    override suspend fun getAllProducts(): Response<ProductListResponse> {
        return companyService.getAllProducts()
    }

    override suspend fun createWithdrawalRequest(withdrawalMoneyRequest: WithdrawalMoneyRequest): Response<BaseResponse> {
        return companyService.createWithdrawalRequest(withdrawalMoneyRequest)
    }

    override suspend fun getGalleryItems(galleryDataRequest: GalleryDataRequest): Response<GalleryResponse> {
        return companyService.getGalleryItems(galleryDataRequest)
    }

    override suspend fun createPaymentLink(paymentLinkRequest: PaymentLinkRequest): Response<PaymentLinkResponse> {
        return companyService.createPaymentLink(paymentLinkRequest)
    }


}