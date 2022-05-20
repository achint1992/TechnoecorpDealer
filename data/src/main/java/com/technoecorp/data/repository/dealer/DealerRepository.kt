package com.technoecorp.data.repository.dealer

import com.technoecorp.data.repository.dealer.datasource.IDealerRemoteDatasource
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.ResultWrapperConvertor
import com.technoecorp.domain.domainmodel.data.Kyc
import com.technoecorp.domain.domainmodel.request.*
import com.technoecorp.domain.domainmodel.response.auth.otp.OtpResponse
import com.technoecorp.domain.domainmodel.response.dealer.citylist.CityWiseCountResponse
import com.technoecorp.domain.domainmodel.response.dealer.dashboard.DashboardAnayliticsResponse
import com.technoecorp.domain.domainmodel.response.dealer.filter.DealerFilterResponse
import com.technoecorp.domain.domainmodel.response.dealer.kyc.KycResponse
import com.technoecorp.domain.domainmodel.response.dealer.statelist.StateWiseCountResponse
import com.technoecorp.domain.repository.IDealerRepository

class DealerRepository(private var dealerRemoteDatasource: IDealerRemoteDatasource) :
    IDealerRepository {


    override suspend fun getDealerAnalyticalData(dealerAnalyticalRequest: DealerAnalyticalRequest): ResultWrapper<DashboardAnayliticsResponse> {
        return ResultWrapperConvertor.responseToResource(
            dealerRemoteDatasource.getDashboardAnalytics(
                dealerAnalyticalRequest
            )
        )
    }

    override suspend fun getFilteredDealerList(dealerFilterRequest: DealerFilterRequest): ResultWrapper<DealerFilterResponse> {
        return ResultWrapperConvertor.responseToResource(
            dealerRemoteDatasource.getFilterDealerList(
                dealerFilterRequest
            )
        )
    }

    override suspend fun getStateWiseDealer(stateWiseDealerCountRequest: StateWiseDealerCountRequest): ResultWrapper<StateWiseCountResponse> {
        return ResultWrapperConvertor.responseToResource(
            dealerRemoteDatasource.getDealerStateWise(
                stateWiseDealerCountRequest
            )
        )
    }

    override suspend fun getCityWiseDealer(cityWiseDealerCountRequest: CityWiseDealerCountRequest): ResultWrapper<CityWiseCountResponse> {
        return ResultWrapperConvertor.responseToResource(
            dealerRemoteDatasource.getDealerCityWise(
                cityWiseDealerCountRequest
            )
        )
    }

    override suspend fun updateDealerProfile(updateProfileRequest: UpdateProfileRequest): ResultWrapper<OtpResponse> {
        return ResultWrapperConvertor.responseToResource(
            dealerRemoteDatasource.updateDealerProfile(
                updateProfileRequest
            )
        )
    }

    override suspend fun updateDealerProfilePic(updateProfileRequest: UpdateProfilePicRequest): ResultWrapper<OtpResponse> {
        return ResultWrapperConvertor.responseToResource(
            dealerRemoteDatasource.updateDealerProfilePic(
                updateProfileRequest
            )
        )
    }

    override suspend fun updateKyc(kyc: Kyc): ResultWrapper<KycResponse> {
        return ResultWrapperConvertor.responseToResource(dealerRemoteDatasource.updateKyc(kyc))
    }

}