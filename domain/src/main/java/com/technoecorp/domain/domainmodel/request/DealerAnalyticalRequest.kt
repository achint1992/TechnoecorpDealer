package com.technoecorp.domain.domainmodel.request

import com.google.gson.annotations.SerializedName

data class DealerAnalyticalRequest(
    @SerializedName("dealerId")
    val dealerId: Int,
    @SerializedName("referCode")
    val referCode: String
)