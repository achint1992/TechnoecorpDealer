package com.technoecorp.domain.domainmodel.response.dealer.statelist


import com.google.gson.annotations.SerializedName

data class StateWiseCount(
    @SerializedName("activeCount")
    val activeCount: Int,
    @SerializedName("count")
    val count: Int,
    @SerializedName("deactiveCount")
    val deactiveCount: Int,
    @SerializedName("stateId")
    val stateId: Int,
    @SerializedName("stateName")
    val stateName: String,
    @SerializedName("color")
    var color: Int = 0
)