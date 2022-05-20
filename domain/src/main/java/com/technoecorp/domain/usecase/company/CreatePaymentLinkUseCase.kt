package com.technoecorp.domain.usecase.company

import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.CityListRequest
import com.technoecorp.domain.domainmodel.request.PaymentLinkRequest
import com.technoecorp.domain.domainmodel.response.auth.login.LoginResponse
import com.technoecorp.domain.domainmodel.response.company.payment.PaymentLinkResponse
import com.technoecorp.domain.repository.ICompanyDataRepository

class CreatePaymentLinkUseCase(private val companyDataRepository: ICompanyDataRepository) {
    suspend fun execute(paymentLinkRequest: PaymentLinkRequest): ResultWrapper<PaymentLinkResponse> =
        companyDataRepository.createPaymentLink(paymentLinkRequest)
}