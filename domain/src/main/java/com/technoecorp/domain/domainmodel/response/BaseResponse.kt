package com.technoecorp.domain.domainmodel.response

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("message")
    val message: String
)