package com.technoecorp.domain.domainmodel.request

import com.google.gson.annotations.SerializedName

data class CityWiseDealerCountRequest(
    @SerializedName("referCode")
    val referCode: String,
    @SerializedName("stateId")
    val stateId: Int)
