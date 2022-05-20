package com.technoecorp.domain.domainmodel.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Kyc(
    @SerializedName("dealerId")
    val dealerId: Int? = null,
    @SerializedName("accountName")
    val accountName: String? = null,
    @SerializedName("accountNumber")
    val accountNumber: String? = null,
    @SerializedName("bankName")
    val bankName: String? = null,
    @SerializedName("branchName")
    val branchName: String? = null,
    @SerializedName("ifscCode")
    val ifscCode: String? = null,
    @SerializedName("accountType")
    val accountType: String? = null,
    @SerializedName("addressProofId")
    val addressProofId: String? = null,
    @SerializedName("userVerificationId")
    val userVerificationId: String? = null,


    )
