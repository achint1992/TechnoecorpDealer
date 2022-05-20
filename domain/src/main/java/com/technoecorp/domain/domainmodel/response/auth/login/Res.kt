package com.technoecorp.domain.domainmodel.response.auth.login


import com.google.gson.annotations.SerializedName

data class Res(
    @SerializedName("message")
    val message: String,
    @SerializedName("type")
    val type: String
)