package com.technoecorp.data.repository.company.datasource

import com.technoecorp.domain.domainmodel.request.*
import com.technoecorp.domain.domainmodel.response.BaseResponse
import com.technoecorp.domain.domainmodel.response.company.city.CityResponse
import com.technoecorp.domain.domainmodel.response.company.country.CountryResponse
import com.technoecorp.domain.domainmodel.response.company.gallery.GalleryResponse
import com.technoecorp.domain.domainmodel.response.company.payment.PaymentLinkResponse
import com.technoecorp.domain.domainmodel.response.company.product.ProductListResponse
import com.technoecorp.domain.domainmodel.response.company.state.StateResponse
import retrofit2.Response

interface ICompanyRemoteDatasource {

    suspend fun getAllCountryList(): Response<CountryResponse>

    suspend fun getAllStateByCountry(stateListRequest: StateListRequest): Response<StateResponse>

    suspend fun getALlCityByState(cityListRequest: CityListRequest): Response<CityResponse>

    suspend fun getAllProducts(): Response<ProductListResponse>

    suspend fun createWithdrawalRequest(withdrawalMoneyRequest: WithdrawalMoneyRequest): Response<BaseResponse>

    suspend fun getGalleryItems(galleryDataRequest: GalleryDataRequest): Response<GalleryResponse>

    suspend fun createPaymentLink(paymentLinkRequest: PaymentLinkRequest): Response<PaymentLinkResponse>
}