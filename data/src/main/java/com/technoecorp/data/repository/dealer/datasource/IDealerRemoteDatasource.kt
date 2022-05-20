package com.technoecorp.data.repository.dealer.datasource

import com.technoecorp.domain.domainmodel.data.Kyc
import com.technoecorp.domain.domainmodel.request.*
import com.technoecorp.domain.domainmodel.response.auth.otp.OtpResponse
import com.technoecorp.domain.domainmodel.response.dealer.citylist.CityWiseCountResponse
import com.technoecorp.domain.domainmodel.response.dealer.dashboard.DashboardAnayliticsResponse
import com.technoecorp.domain.domainmodel.response.dealer.filter.DealerFilterResponse
import com.technoecorp.domain.domainmodel.response.dealer.kyc.KycResponse
import com.technoecorp.domain.domainmodel.response.dealer.statelist.StateWiseCountResponse
import retrofit2.Response

interface IDealerRemoteDatasource {
    suspend fun getDashboardAnalytics(dealerAnalyticalRequest: DealerAnalyticalRequest): Response<DashboardAnayliticsResponse>

    suspend fun getFilterDealerList(dealerFilterRequest: DealerFilterRequest): Response<DealerFilterResponse>

    suspend fun getDealerStateWise(stateWiseDealerCountRequest: StateWiseDealerCountRequest): Response<StateWiseCountResponse>

    suspend fun getDealerCityWise(cityWiseDealerCountRequest: CityWiseDealerCountRequest): Response<CityWiseCountResponse>

    suspend fun updateDealerProfile(updateProfileRequest: UpdateProfileRequest): Response<OtpResponse>

    suspend fun updateDealerProfilePic(updateProfileRequest: UpdateProfilePicRequest): Response<OtpResponse>

    suspend fun updateKyc(kyc: Kyc): Response<KycResponse>
}