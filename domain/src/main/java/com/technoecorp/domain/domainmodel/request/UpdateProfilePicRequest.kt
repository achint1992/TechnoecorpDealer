package com.technoecorp.domain.domainmodel.request

import com.google.gson.annotations.SerializedName

data class UpdateProfilePicRequest(
    @SerializedName("dealerId")
    val dealerId: Int,
    @SerializedName("profilePic")
    val profilePic: String? = null,

    )