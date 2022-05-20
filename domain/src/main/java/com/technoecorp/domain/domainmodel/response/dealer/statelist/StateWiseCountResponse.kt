package com.technoecorp.domain.domainmodel.response.dealer.statelist


import com.google.gson.annotations.SerializedName

data class StateWiseCountResponse(
    @SerializedName("data")
    val `data`: List<StateWiseCount> = ArrayList(),
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)