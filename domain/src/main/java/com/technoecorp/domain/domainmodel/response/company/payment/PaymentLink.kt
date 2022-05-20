package com.technoecorp.domain.domainmodel.response.company.payment


import com.google.gson.annotations.SerializedName

data class PaymentLink(
    @SerializedName("redirectUrl")
    val redirectUrl: String
)