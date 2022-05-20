package com.technoecorp.domain.domainmodel.response.auth.otp


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("dealer")
    val dealer: Dealer,
    @SerializedName("dealerId")
    val dealerId: Int,
    @SerializedName("distributorId")
    val distributorId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("username")
    val username: String
)