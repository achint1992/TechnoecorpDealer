package com.technoecorp.domain.domainmodel.response.dealer.kyc


import com.google.gson.annotations.SerializedName

data class KycDetail(
    @SerializedName("accountName")
    val accountName: String,
    @SerializedName("accountNumber")
    val accountNumber: String,
    @SerializedName("accountType")
    val accountType: String,
    @SerializedName("addressProofId")
    val addressProofId: String,
    @SerializedName("bankName")
    val bankName: String,
    @SerializedName("bankProofId")
    val bankProofId: Any,
    @SerializedName("branchName")
    val branchName: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("dealerId")
    val dealerId: Int,
    @SerializedName("distributorId")
    val distributorId: Any,
    @SerializedName("ifscCode")
    val ifscCode: String,
    @SerializedName("isKycRejected")
    val isKycRejected: Int,
    @SerializedName("isKycVerify")
    val isKycVerify: Int,
    @SerializedName("kycId")
    val kycId: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("userVerificationId")
    val userVerificationId: String
)