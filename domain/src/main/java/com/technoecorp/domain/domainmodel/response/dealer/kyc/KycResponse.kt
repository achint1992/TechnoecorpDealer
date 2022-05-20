package com.technoecorp.domain.domainmodel.response.dealer.kyc

import com.google.gson.annotations.SerializedName

data class KycResponse(
    @SerializedName("statusCode")
    var statusCode: Int? = null,

    @SerializedName("status")
    var status: Boolean? = null,

    @SerializedName("message")
    var message: String? = null,

    @SerializedName("data")
    val data: KycDetail? = null
)