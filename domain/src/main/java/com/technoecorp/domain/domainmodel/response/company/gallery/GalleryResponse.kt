package com.technoecorp.domain.domainmodel.response.company.gallery


import com.google.gson.annotations.SerializedName

data class GalleryResponse(
    @SerializedName("data")
    val `data`: List<Gallery>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)