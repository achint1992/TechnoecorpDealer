package com.technoecorp.domain.domainmodel.response

import com.google.gson.annotations.SerializedName
import com.technoecorp.domain.domainmodel.data.Dealer
import com.technoecorp.domain.domainmodel.response.dealer.dashboard.WithdrawalData

data class DashboardAnalytics(
    @SerializedName("id")
    val id: Int,
    @SerializedName("colorCodeCard")
    val colorCodeCard: String,
    @SerializedName("icon")
    val icon: Int,
    @SerializedName("textHeader")
    val textHeader: String,
    @SerializedName("value")
    val value: String,
    @SerializedName("type")
    val type: DashboardAnalyticsType,
    @SerializedName("dealerIncomeData")
    val dealerIncomeData: DealerListIncome?,
    @SerializedName("dealerDataType")
    val dealerDataType: String?,
    @SerializedName("withdrawalData")
    val withdrawalData: WithdrawalData?
)

enum class DashboardAnalyticsType {
    @SerializedName("INCOME")
    INCOME,

    @SerializedName("DEALER_LIST")
    DEALER_LIST,

    @SerializedName("NONE")
    NONE,

    @SerializedName("WITHDRAWAL")
    WITHDRAWAL
}


data class DealerListIncome(
    @SerializedName("dealers")
    val dealers: List<Dealer>,
    @SerializedName("title")
    val title: String,
    @SerializedName("isExport")
    val isExport: Boolean,
)