package com.technoecorp.data.repository.dealer.datasource

import com.technoecorp.data.api.DealerService
import com.technoecorp.domain.domainmodel.data.Kyc
import com.technoecorp.domain.domainmodel.request.*
import com.technoecorp.domain.domainmodel.response.auth.otp.OtpResponse
import com.technoecorp.domain.domainmodel.response.dealer.citylist.CityWiseCountResponse
import com.technoecorp.domain.domainmodel.response.dealer.dashboard.DashboardAnayliticsResponse
import com.technoecorp.domain.domainmodel.response.dealer.filter.DealerFilterResponse
import com.technoecorp.domain.domainmodel.response.dealer.kyc.KycResponse
import com.technoecorp.domain.domainmodel.response.dealer.statelist.StateWiseCountResponse
import retrofit2.Response

class DealerRemoteDatasource(private var dealerService: DealerService) : IDealerRemoteDatasource {

    override suspend fun getDashboardAnalytics(dealerAnalyticalRequest: DealerAnalyticalRequest): Response<DashboardAnayliticsResponse> {
        return dealerService.getDealerAnalyticalData(dealerAnalyticalRequest)
    }

    override suspend fun getFilterDealerList(dealerFilterRequest: DealerFilterRequest): Response<DealerFilterResponse> {
        return dealerService.getDealerFilterResponse(dealerFilterRequest)
    }

    override suspend fun getDealerStateWise(stateWiseDealerCountRequest: StateWiseDealerCountRequest): Response<StateWiseCountResponse> {
        return dealerService.getDealerListStateWise(stateWiseDealerCountRequest)
    }

    override suspend fun getDealerCityWise(cityWiseDealerCountRequest: CityWiseDealerCountRequest): Response<CityWiseCountResponse> {
        return dealerService.getDealerListCityWise(cityWiseDealerCountRequest)
    }

    override suspend fun updateDealerProfile(updateProfileRequest: UpdateProfileRequest): Response<OtpResponse> {
        return dealerService.updateDealerProfile(updateProfileRequest)
    }

    override suspend fun updateDealerProfilePic(updateProfileRequest: UpdateProfilePicRequest): Response<OtpResponse> {
        return dealerService.updateDealerProfilePic(updateProfileRequest)
    }

    override suspend fun updateKyc(kyc: Kyc): Response<KycResponse> {
        return dealerService.updateKyc(kyc)
    }
}