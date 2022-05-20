package com.technoecorp.domain.domainmodel.response.company.product


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("isActive")
    val isActive: Int,
    @SerializedName("package")
    val packageX: List<Package> = ArrayList(),
    @SerializedName("productDescription")
    val productDescription: String,
    @SerializedName("productId")
    val productId: Int,
    @SerializedName("productName")
    val productName: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)