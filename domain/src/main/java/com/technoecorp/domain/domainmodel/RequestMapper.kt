package com.technoecorp.domain.domainmodel

import com.technoecorp.domain.domainmodel.data.Dealer
import com.technoecorp.domain.domainmodel.request.UpdateProfilePicRequest
import com.technoecorp.domain.domainmodel.request.UpdateProfileRequest


fun Dealer.toUpateProfileRequest(): UpdateProfileRequest {
    return UpdateProfileRequest(
        dealerId = this.dealerId,
        email = this.email,
        alternetMobileNo1 = this.alternetMobileNo1,
        userAddress = this.userAddress,
        districtName = this.districtName,
        countryId = this.countryId,
        stateId = this.stateId,
        cityId = this.cityId,
        pincode = this.pincode,
        fatherName = this.fatherName,
        dob = this.dob
    )
}

fun Dealer.toUpdateProfilePicRequest(): UpdateProfilePicRequest {
    return UpdateProfilePicRequest(
        dealerId = this.dealerId,
        profilePic = this.profilePic
    )
}
