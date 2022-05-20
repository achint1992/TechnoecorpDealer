package com.technoecorp.domain.domainmodel.response.dealer.dashboard


import com.google.gson.annotations.SerializedName
import com.technoecorp.domain.domainmodel.data.Dealer

data class AnalyticsData(
    @SerializedName("activeDealer")
    val activeDealer: Int = 0,
    @SerializedName("allDealerCount")
    val allDealerCount: Int = 0,
    @SerializedName("deactiveDealer")
    val deactiveDealer: Int = 0,
    @SerializedName("lastMonthBusiness")
    val lastMonthBusiness: Int = 0,
    @SerializedName("lastMonthList")
    val lastMonthList: List<Dealer> = ArrayList(),
    @SerializedName("lastWeekBusiness")
    val lastWeekBusiness: Int = 0,
    @SerializedName("lastWeekList")
    val lastWeekList: List<Dealer> = ArrayList(),
    @SerializedName("recentDealers")
    val recentDealers: List<String> = ArrayList(),
    @SerializedName("todayBusiness")
    val todayBusiness: Int = 0,
    @SerializedName("todayList")
    val todayList: List<Dealer> = ArrayList(),
    @SerializedName("totalBusiness")
    val totalBusiness: Int = 0,
    @SerializedName("withdrawalData")
    val withdrawalData: WithdrawalData?
)