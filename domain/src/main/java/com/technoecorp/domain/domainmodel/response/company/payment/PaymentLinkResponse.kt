package com.technoecorp.domain.domainmodel.response.company.payment


import com.google.gson.annotations.SerializedName

data class PaymentLinkResponse(
    @SerializedName("data")
    val `data`: PaymentLink,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)