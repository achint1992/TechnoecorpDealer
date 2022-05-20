package com.technoecorp.domain.domainmodel.request

import com.google.gson.annotations.SerializedName

data class StateWiseDealerCountRequest(
    @SerializedName("referCode")
    val referCode: String
)
