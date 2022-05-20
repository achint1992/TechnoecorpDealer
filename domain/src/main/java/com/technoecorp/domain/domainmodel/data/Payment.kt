package com.technoecorp.domain.domainmodel.data

import com.google.gson.annotations.SerializedName

data class Payment(
    @SerializedName("payment_id")
    val paymentId: String = ""
)