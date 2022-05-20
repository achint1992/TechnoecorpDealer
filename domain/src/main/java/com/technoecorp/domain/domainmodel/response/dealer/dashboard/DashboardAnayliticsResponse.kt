package com.technoecorp.domain.domainmodel.response.dealer.dashboard


import com.google.gson.annotations.SerializedName

data class DashboardAnayliticsResponse(
    @SerializedName("data")
    val `data`: AnalyticsData,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)