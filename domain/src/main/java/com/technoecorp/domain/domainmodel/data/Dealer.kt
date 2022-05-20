package com.technoecorp.domain.domainmodel.data

import com.google.gson.annotations.SerializedName
import com.technoecorp.domain.domainmodel.response.dealer.kyc.KycDetail


data class Dealer(
    @SerializedName("dealerId")
    val dealerId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("mobileNo")
    val mobileNo: String,
    @SerializedName("email")
    val email: String = "",
    @SerializedName("alternetMobileNo1")
    val alternetMobileNo1: String = "",
    @SerializedName("userAddress")
    val userAddress: String = "",
    @SerializedName("districtName")
    val districtName: String = "",
    @SerializedName("countryId")
    val countryId: Int? = Int.MIN_VALUE,
    @SerializedName("stateId")
    val stateId: Int? = Int.MIN_VALUE,
    @SerializedName("cityId")
    val cityId: Int? = Int.MIN_VALUE,
    @SerializedName("pincode")
    val pincode: String = "",
    @SerializedName("referCode")
    val referCode: String? = "",
    @SerializedName("profilePic")
    val profilePic: String? = "",
    @SerializedName("fatherName")
    val fatherName: String = "",
    @SerializedName("dob")
    val dob: String = "",
    @SerializedName("country")
    val country: Country? = null,
    @SerializedName("state")
    val state: State? = null,
    @SerializedName("city")
    val city: City? = null,
    @SerializedName("payment")
    val payment: ArrayList<Payment> = ArrayList<Payment>(),
    @SerializedName("KycDetail")
    var kycDetail: KycDetail? = null,


    )


