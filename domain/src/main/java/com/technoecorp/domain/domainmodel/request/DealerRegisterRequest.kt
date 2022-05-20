package com.technoecorp.domain.domainmodel.request

import com.google.gson.annotations.SerializedName

data class DealerRegisterRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("mobileNo")
    val mobileNo: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("referBy")
    val referBy: String
)