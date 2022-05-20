package com.technoecorp.data.api

import com.technoecorp.domain.domainmodel.response.dealer.dashboard.DashboardAnayliticsResponse
import com.technoecorp.domain.domainmodel.response.dealer.citylist.CityWiseCountResponse
import com.technoecorp.domain.domainmodel.response.dealer.statelist.StateWiseCountResponse
import com.technoecorp.domain.domainmodel.response.dealer.filter.DealerFilterResponse
import com.technoecorp.domain.domainmodel.response.dealer.kyc.KycResponse
import com.technoecorp.domain.domainmodel.response.auth.otp.OtpResponse
import com.technoecorp.domain.domainmodel.data.Kyc
import com.technoecorp.domain.domainmodel.request.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DealerService {
    @POST("dealer/getDealerCountById")
    suspend fun getDealerAnalyticalData(@Body dealerAnalyticalRequest: DealerAnalyticalRequest): Response<DashboardAnayliticsResponse>

    @POST("dealer/getFilterDealer")
    suspend fun getDealerFilterResponse(@Body dealerFilterRequest: DealerFilterRequest): Response<DealerFilterResponse>

    @POST("dealer/getStateWiseCount")
    suspend fun getDealerListStateWise(@Body stateWiseDealerCountRequest: StateWiseDealerCountRequest): Response<StateWiseCountResponse>

    @POST("dealer/getCityCountByStateId")
    suspend fun getDealerListCityWise(@Body cityWiseDealerCountRequest: CityWiseDealerCountRequest): Response<CityWiseCountResponse>

    @POST("dealer/upateProfiledetails")
    suspend fun updateDealerProfile(@Body updateProfileRequest: UpdateProfileRequest): Response<OtpResponse>

    @POST("dealer/upateProfiledetails")
    suspend fun updateDealerProfilePic(@Body updateProfileRequest: UpdateProfilePicRequest): Response<OtpResponse>

    @POST("dealer/createKycInfo")
    suspend fun updateKyc(@Body kyc: Kyc): Response<KycResponse>

}