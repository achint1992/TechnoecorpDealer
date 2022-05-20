package com.technoecorp.domain.domainmodel.response.company.country

import com.google.gson.annotations.SerializedName
import com.technoecorp.domain.domainmodel.response.auth.otp.Country

data class CountryResponse(
    @SerializedName("data")
    val `data`: List<Country> = ArrayList(),
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
)