package com.technoecorp.domain.domainmodel.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("cityId")
    var cityId: Int? = -1,
    @SerializedName("cityName")
    val cityName: String? = null

)