package com.technoecorp.domain.domainmodel.response.company.product


import com.google.gson.annotations.SerializedName

data class Package(
    @SerializedName("companyAmount")
    val companyAmount: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("dealerAmount")
    val dealerAmount: String,
    @SerializedName("distributorAmount")
    val distributorAmount: String,
    @SerializedName("isAmountDistribute")
    val isAmountDistribute: Int,
    @SerializedName("packageId")
    val packageId: Int,
    @SerializedName("packageImg")
    val packageImg: String,
    @SerializedName("packageName")
    val packageName: String,
    @SerializedName("packageOffer")
    val packageOffer: Any,
    @SerializedName("packagePrice")
    val packagePrice: String,
    @SerializedName("packageQty")
    val packageQty: String,
    @SerializedName("productId")
    val productId: Int,
    @SerializedName("updatedAt")
    val updatedAt: String
)