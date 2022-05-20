package com.technoecorp.domain.domainmodel.response.company.city

import com.google.gson.annotations.SerializedName
import com.technoecorp.domain.domainmodel.response.auth.otp.City

data class CityResponse(
    @SerializedName("data")
    val `data`: List<City> = ArrayList(),
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
)