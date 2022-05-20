package com.technoecorp.domain.domainmodel.response.auth.otp


import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("cityId")
    val cityId: Int,
    @SerializedName("cityName")
    val cityName: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("stateId")
    val stateId: Int,
    @SerializedName("updatedAt")
    val updatedAt: String
) : GeoList() {
    override fun getCompareValue(): String {
        return cityName
    }

    override fun getDisplayName(): String {
        return cityName
    }

}