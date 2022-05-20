package com.technoecorp.domain.repository

import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.data.Kyc
import com.technoecorp.domain.domainmodel.request.*
import com.technoecorp.domain.domainmodel.response.auth.otp.OtpResponse
import com.technoecorp.domain.domainmodel.response.dealer.citylist.CityWiseCountResponse
import com.technoecorp.domain.domainmodel.response.dealer.dashboard.DashboardAnayliticsResponse
import com.technoecorp.domain.domainmodel.response.dealer.filter.DealerFilterResponse
import com.technoecorp.domain.domainmodel.response.dealer.kyc.KycResponse
import com.technoecorp.domain.domainmodel.response.dealer.statelist.StateWiseCountResponse

interface IDealerRepository {
    suspend fun getDealerAnalyticalData(dealerAnalyticalRequest: DealerAnalyticalRequest): ResultWrapper<DashboardAnayliticsResponse>

    suspend fun getFilteredDealerList(dealerFilterRequest: DealerFilterRequest): ResultWrapper<DealerFilterResponse>

    suspend fun getStateWiseDealer(stateWiseDealerCountRequest: StateWiseDealerCountRequest): ResultWrapper<StateWiseCountResponse>

    suspend fun getCityWiseDealer(cityWiseDealerCountRequest: CityWiseDealerCountRequest): ResultWrapper<CityWiseCountResponse>

    suspend fun updateDealerProfile(updateProfileRequest: UpdateProfileRequest): ResultWrapper<OtpResponse>

    suspend fun updateDealerProfilePic(updateProfileRequest: UpdateProfilePicRequest): ResultWrapper<OtpResponse>

    suspend fun updateKyc(kyc: Kyc): ResultWrapper<KycResponse>

    // suspend fun getDealerProfile()
}