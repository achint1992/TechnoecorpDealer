package com.technoecorp.domain.domainmodel.response.dealer.dashboard


import com.google.gson.annotations.SerializedName

data class WithdrawalData(
    @SerializedName("count")
    val count: Int=0,
    @SerializedName("withdrawalAmount")
    val withdrawalAmount: Int=0
)