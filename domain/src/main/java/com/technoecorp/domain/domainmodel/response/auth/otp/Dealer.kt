package com.technoecorp.domain.domainmodel.response.auth.otp


import com.google.gson.annotations.SerializedName
import com.technoecorp.domain.domainmodel.response.dealer.kyc.KycDetail

data class Dealer(
    @SerializedName("alternetMobileNo1")
    val alternetMobileNo1: String,
    @SerializedName("city")
    val city: City,
    @SerializedName("cityId")
    val cityId: Int,
    @SerializedName("country")
    val country: Country,
    @SerializedName("countryId")
    val countryId: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("dealerId")
    val dealerId: Int,
    @SerializedName("distributorId")
    val distributorId: Int,
    @SerializedName("districtName")
    val districtName: String,
    @SerializedName("dob")
    val dob: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("fatherName")
    val fatherName: String,
    @SerializedName("gender")
    val gender: Any,
    @SerializedName("isActive")
    val isActive: Int,
    @SerializedName("isVerifed")
    val isVerifed: Int,
    @SerializedName("KycDetail")
    val kycDetail: KycDetail,
    @SerializedName("mobileNo")
    val mobileNo: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("payment")
    val payment: List<Any>,
    @SerializedName("pincode")
    val pincode: String,
    @SerializedName("profilePic")
    val profilePic: String,
    @SerializedName("referBy")
    val referBy: String,
    @SerializedName("referCode")
    val referCode: String,
    @SerializedName("state")
    val state: State,
    @SerializedName("stateId")
    val stateId: Int,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("type")
    val type: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("userAddress")
    val userAddress: String
)