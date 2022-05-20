package com.technoecorp.domain.domainmodel.response.dealer.citylist


import com.google.gson.annotations.SerializedName

data class CityWiseCountResponse(
    @SerializedName("data")
    val `data`: List<CityWiseCount> = ArrayList(),
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)