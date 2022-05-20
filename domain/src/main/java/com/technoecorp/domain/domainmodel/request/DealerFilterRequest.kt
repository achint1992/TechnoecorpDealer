package com.technoecorp.domain.domainmodel.request

import com.google.gson.annotations.SerializedName

data class DealerFilterRequest(
    @SerializedName("dealerId")
    val dealerId: Int,
    @SerializedName("referCode")
    val referCode: String,
    @SerializedName("isActive")
    val isActive: Boolean? = null,
    @SerializedName("stateId")
    val stateId: Int? = null,
    @SerializedName("cityId")
    val cityId: Int? = null,
    @SerializedName("startDate")
    val startDate: String = "",
    @SerializedName("endDate")
    val endDate: String = ""
)
