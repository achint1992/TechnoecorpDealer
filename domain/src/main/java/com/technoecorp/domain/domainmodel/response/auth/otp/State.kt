package com.technoecorp.domain.domainmodel.response.auth.otp


import com.google.gson.annotations.SerializedName

data class State(
    @SerializedName("countryId")
    val countryId: Int,
    @SerializedName("stateId")
    val stateId: Int,
    @SerializedName("stateName")
    val stateName: String
): GeoList() {
    override fun getCompareValue(): String {
        return stateName
    }

    override fun getDisplayName(): String {
        return stateName
    }

}