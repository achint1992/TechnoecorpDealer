package com.technoecorp.domain.domainmodel.response.auth.otp


import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("countryDialCode")
    val countryDialCode: String,
    @SerializedName("countryName")
    val countryName: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("updatedAt")
    val updatedAt: String
) : GeoList() {
    override fun getCompareValue(): String {
        return countryName
    }

    override fun getDisplayName(): String {
        return "($countryDialCode) - $countryName"
    }

}

abstract class GeoList {
    abstract fun getCompareValue(): String
    abstract fun getDisplayName(): String
}