package com.technoecorp.domain.domainmodel.request

import com.google.gson.annotations.SerializedName

data class DealerValidationRequest(
    @SerializedName("refId")
    val refId: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("otp")
    val otp: String,
    @SerializedName("type")
    val type: String = "login"
)
