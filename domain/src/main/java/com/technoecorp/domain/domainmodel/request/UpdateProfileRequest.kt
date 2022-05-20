package com.technoecorp.domain.domainmodel.request

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("dealerId")
    val dealerId: Int,
    @SerializedName("email")
    val email: String = "",
    @SerializedName("alternetMobileNo1")
    val alternetMobileNo1: String = "",
    @SerializedName("userAddress")
    val userAddress: String = "",
    @SerializedName("districtName")
    val districtName: String = "",
    @SerializedName("countryId")
    val countryId: Int? = null,
    @SerializedName("stateId")
    val stateId: Int? = null,
    @SerializedName("cityId")
    val cityId: Int? = null,
    @SerializedName("pincode")
    val pincode: String = "",
    @SerializedName("fatherName")
    val fatherName: String = "",
    @SerializedName("dob")
    val dob: String = "",
    )