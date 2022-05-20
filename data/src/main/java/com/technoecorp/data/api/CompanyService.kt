package com.technoecorp.data.api

import com.technoecorp.domain.domainmodel.response.BaseResponse
import com.technoecorp.domain.domainmodel.response.company.city.CityResponse
import com.technoecorp.domain.domainmodel.response.company.country.CountryResponse
import com.technoecorp.domain.domainmodel.response.company.gallery.GalleryResponse
import com.technoecorp.domain.domainmodel.response.company.payment.PaymentLinkResponse
import com.technoecorp.domain.domainmodel.response.company.product.ProductListResponse
import com.technoecorp.domain.domainmodel.response.company.state.StateResponse
import com.technoecorp.domain.domainmodel.request.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CompanyService {

    @GET("auth/getCountryList")
    suspend fun getAllCountryList(): Response<CountryResponse>

    @POST("auth/getStateByCountryId")
    suspend fun getAllStateByCountry(@Body stateListRequest: StateListRequest): Response<StateResponse>

    @POST("auth/getAllCities")
    suspend fun getAllCityByState(@Body cityListRequest: CityListRequest): Response<CityResponse>

    @GET("dealer/getAllProductWithPackage")
    suspend fun getAllProducts(): Response<ProductListResponse>

    @POST("dealer/createWidthralRequest")
    suspend fun createWithdrawalRequest(@Body withdrawalMoneyRequest: WithdrawalMoneyRequest): Response<BaseResponse>

    @POST("auth/getRecentMedia")
    suspend fun getGalleryItems(@Body galleryDataRequest: GalleryDataRequest): Response<GalleryResponse>

    @POST("dealer/createPaymentRequest")
    suspend fun createPaymentLink(@Body paymentLinkRequest: PaymentLinkRequest): Response<PaymentLinkResponse>

}