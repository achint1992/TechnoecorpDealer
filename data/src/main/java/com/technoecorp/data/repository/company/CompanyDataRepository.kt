package com.technoecorp.data.repository.company

import com.technoecorp.data.repository.company.datasource.ICompanyRemoteDatasource
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.ResultWrapperConvertor
import com.technoecorp.domain.domainmodel.request.*
import com.technoecorp.domain.domainmodel.response.BaseResponse
import com.technoecorp.domain.domainmodel.response.company.city.CityResponse
import com.technoecorp.domain.domainmodel.response.company.country.CountryResponse
import com.technoecorp.domain.domainmodel.response.company.gallery.GalleryResponse
import com.technoecorp.domain.domainmodel.response.company.payment.PaymentLinkResponse
import com.technoecorp.domain.domainmodel.response.company.product.ProductListResponse
import com.technoecorp.domain.domainmodel.response.company.state.StateResponse
import com.technoecorp.domain.repository.ICompanyDataRepository
import retrofit2.Response

class CompanyDataRepository(private var companyRemoteDatasource: ICompanyRemoteDatasource) :
    ICompanyDataRepository {


    override suspend fun getCountryData(): ResultWrapper<CountryResponse> {
        return ResultWrapperConvertor.responseToResource(companyRemoteDatasource.getAllCountryList())
    }


    override suspend fun getCityData(cityListRequest: CityListRequest): ResultWrapper<CityResponse> {
        return ResultWrapperConvertor.responseToResource(
            companyRemoteDatasource.getALlCityByState(
                cityListRequest
            )
        )
    }

    override suspend fun getStateData(stateListRequest: StateListRequest): ResultWrapper<StateResponse> {
        return ResultWrapperConvertor.responseToResource(
            companyRemoteDatasource.getAllStateByCountry(
                stateListRequest
            )
        )
    }

    override suspend fun getProductData(): ResultWrapper<ProductListResponse> {
        return ResultWrapperConvertor.responseToResource(companyRemoteDatasource.getAllProducts())
    }

    override suspend fun createPaymentLink(paymentLinkRequest: PaymentLinkRequest): ResultWrapper<PaymentLinkResponse> {
        return ResultWrapperConvertor.responseToResource(
            companyRemoteDatasource.createPaymentLink(
                paymentLinkRequest
            )
        )
    }

    override suspend fun getGalleryData(galleryDataRequest: GalleryDataRequest): ResultWrapper<GalleryResponse> {
        return ResultWrapperConvertor.responseToResource(
            companyRemoteDatasource.getGalleryItems(
                galleryDataRequest
            )
        )
    }

    override suspend fun createWithdrawalRequest(withdrawalMoneyRequest: WithdrawalMoneyRequest): ResultWrapper<BaseResponse> {
        return ResultWrapperConvertor.responseToResource(
            companyRemoteDatasource.createWithdrawalRequest(
                withdrawalMoneyRequest
            )
        )
    }

}