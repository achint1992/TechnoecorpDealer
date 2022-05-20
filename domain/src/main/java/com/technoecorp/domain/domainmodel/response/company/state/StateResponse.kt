package com.technoecorp.domain.domainmodel.response.company.state

import com.google.gson.annotations.SerializedName
import com.technoecorp.domain.domainmodel.response.auth.otp.State

data class StateResponse(
    @SerializedName("data")
    val `data`: List<State> = ArrayList(),
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
)