package com.technoecorp.domain.domainmodel.response.auth.otp


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("data")
    val `data`: User,
    @SerializedName("token")
    val token: String
)