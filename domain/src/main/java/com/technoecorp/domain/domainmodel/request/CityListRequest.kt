package com.technoecorp.domain.domainmodel.request

import com.google.gson.annotations.SerializedName

data class CityListRequest(
    @SerializedName("stateId")
    val stateId: Int
)