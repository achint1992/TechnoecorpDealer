package com.technoecorp.domain.domainmodel.response.dealer.citylist


import com.google.gson.annotations.SerializedName

data class CityWiseCount(
    @SerializedName("activeCount")
    val activeCount: Int,
    @SerializedName("cityId")
    val cityId: Int,
    @SerializedName("cityName")
    val cityName: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("deactiveCount")
    val deactiveCount: Int,
    @SerializedName("stateName")
    val stateName: String
)