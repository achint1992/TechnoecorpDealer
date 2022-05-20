package com.technoecorp.domain.domainmodel.response.auth.otp


import com.google.gson.annotations.SerializedName

data class OtpResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)