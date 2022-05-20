package com.technoecorp.domain.domainmodel.response.auth.register

import com.google.gson.annotations.SerializedName

data class RegistrationResponse(
    @SerializedName("statusCode")
    var statusCode: Int = Int.MIN_VALUE,

    @SerializedName("status")
    var status: Boolean = false,

    @SerializedName("messge")
    var message: String ="",

    @SerializedName("token")
    var token: String = ""
)