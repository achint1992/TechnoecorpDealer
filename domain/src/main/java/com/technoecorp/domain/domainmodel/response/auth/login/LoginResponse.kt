package com.technoecorp.domain.domainmodel.response.auth.login


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("refId")
    val refId: String,
    @SerializedName("res")
    val res: Res,
    @SerializedName("status")
    val status: Boolean
)