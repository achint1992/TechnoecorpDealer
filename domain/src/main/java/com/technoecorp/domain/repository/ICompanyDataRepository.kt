package com.technoecorp.domain.repository

import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.*
import com.technoecorp.domain.domainmodel.response.BaseResponse
import com.technoecorp.domain.domainmodel.response.company.city.CityResponse
import com.technoecorp.domain.domainmodel.response.company.country.CountryResponse
import com.technoecorp.domain.domainmodel.response.company.gallery.GalleryResponse
import com.technoecorp.domain.domainmodel.response.company.payment.PaymentLinkResponse
import com.technoecorp.domain.domainmodel.response.company.product.ProductListResponse
import com.technoecorp.domain.domainmodel.response.company.state.StateResponse
import retrofit2.Response

interface ICompanyDataRepository {
    suspend fun getCountryData(): ResultWrapper<CountryResponse>

    suspend fun getCityData(cityListRequest: CityListRequest): ResultWrapper<CityResponse>

    suspend fun getStateData(stateListRequest: StateListRequest): ResultWrapper<StateResponse>

    suspend fun getProductData(): ResultWrapper<ProductListResponse>

    suspend fun createPaymentLink(paymentLinkRequest: PaymentLinkRequest): ResultWrapper<PaymentLinkResponse>

    suspend fun getGalleryData(galleryDataRequest: GalleryDataRequest): ResultWrapper<GalleryResponse>

    suspend fun createWithdrawalRequest(withdrawalMoneyRequest: WithdrawalMoneyRequest): ResultWrapper<BaseResponse>


}