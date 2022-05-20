package com.technoecorp.domain.domainmodel.data

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("id")
    var id: Int? = -1,
    @SerializedName("countryName")
    val countryName: String? = null,
    @SerializedName("countryDialCode")
    val countryDialCode: String? = null

)