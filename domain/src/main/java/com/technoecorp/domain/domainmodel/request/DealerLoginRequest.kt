package com.technoecorp.domain.domainmodel.request

import com.google.gson.annotations.SerializedName

data class DealerLoginRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("countryCode")
    val countryCode: String
)
