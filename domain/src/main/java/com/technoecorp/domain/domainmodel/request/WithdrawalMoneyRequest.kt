package com.technoecorp.domain.domainmodel.request

import com.google.gson.annotations.SerializedName

data class WithdrawalMoneyRequest(
    @SerializedName("dealerId")
    val dealerId: Int
)