package com.technoecorp.domain.domainmodel.response.dealer.filter

import com.google.gson.annotations.SerializedName
import com.technoecorp.domain.domainmodel.response.auth.otp.Dealer

data class DealerFilterResponse(

    @SerializedName("status")
    var status: Boolean? = null,

    @SerializedName("message")
    var message: String? = null,

    @SerializedName("data")
    val data: List<Dealer> = ArrayList()
)