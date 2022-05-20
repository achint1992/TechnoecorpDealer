package com.technoecorp.domain.domainmodel.request

import com.google.gson.annotations.SerializedName

data class GalleryDataRequest(
    @SerializedName("type")
    val type: String
)