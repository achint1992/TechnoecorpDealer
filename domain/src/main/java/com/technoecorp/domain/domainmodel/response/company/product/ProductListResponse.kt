package com.technoecorp.domain.domainmodel.response.company.product


import com.google.gson.annotations.SerializedName

data class ProductListResponse(
    @SerializedName("data")
    val `data`: List<Product> = ArrayList(),
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)