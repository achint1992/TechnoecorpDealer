package com.technoecorp.domain.domainmodel.request

import com.google.gson.annotations.SerializedName

data class PaymentLinkRequest(
    @SerializedName("productId")
    val productId: Int,
    @SerializedName("packageId")
    val packageId: Int,
    @SerializedName("dealerId")
    val dealerId: Int,
    @SerializedName("productBuyQty")
    val productBuyQty: Int,
    @SerializedName("totalAmount")
    val totalAmount: Int
)