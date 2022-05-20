package com.technoecorp.domain.domainmodel.request

import com.google.gson.annotations.SerializedName

data class StateListRequest(
    @SerializedName("countryId")
    val countryId: Int
)