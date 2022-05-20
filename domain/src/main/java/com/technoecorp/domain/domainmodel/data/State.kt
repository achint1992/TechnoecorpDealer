package com.technoecorp.domain.domainmodel.data

import com.google.gson.annotations.SerializedName

data class State(
    @SerializedName("stateId")
     val stateId: Int = -1,
    @SerializedName("stateName")
     val stateName: String? = null
)