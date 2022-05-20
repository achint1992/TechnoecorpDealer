package com.technoecorp.gorilladealer.extensions

import com.technoecorp.domain.domainmodel.response.DashboardAnalytics
import com.technoecorp.domain.domainmodel.response.DashboardAnalyticsType
import com.technoecorp.domain.domainmodel.response.DealerListIncome
import com.technoecorp.domain.domainmodel.response.dealer.dashboard.DashboardAnayliticsResponse
import com.technoecorp.gorilladealer.R

fun DashboardAnayliticsResponse.toDashboardAnalytics(): ArrayList<DashboardAnalytics> {
    val dashboardData = this.data.let {

        val dataList: ArrayList<DashboardAnalytics> = ArrayList()
        dataList.add(
            DashboardAnalytics(
                1,
                "#0c3c7c",
                R.drawable.business,
                "Total Business",
                ((it.activeDealer + it.deactiveDealer) * 7500).toString(),
                DashboardAnalyticsType.NONE, null, null, null
            )
        )

        dataList.add(
            DashboardAnalytics(
                2,
                "#1b5e20",
                R.drawable.money_bag,
                "Total Income",
                it.totalBusiness.toString(),
                DashboardAnalyticsType.NONE, null, null, null
            )
        )

        dataList.add(
            DashboardAnalytics(
                3,
                "#673AB7",
                R.drawable.income,
                "One Day Income",
                (it.todayBusiness).toString(),
                DashboardAnalyticsType.INCOME,
                DealerListIncome(it.todayList, "One Day Income", false),
                null, null
            )
        )

        dataList.add(
            DashboardAnalytics(
                4,
                "#FFA000",
                R.drawable.cash,
                "One Week Income",
                (it.lastWeekBusiness).toString(),
                DashboardAnalyticsType.INCOME,
                DealerListIncome(it.lastWeekList, "One Week Income", false),
                null, null

            )
        )

        dataList.add(
            DashboardAnalytics(
                5,
                "#F44336",
                R.drawable.salary,
                "One Month Income",
                (it.lastMonthBusiness).toString(),
                DashboardAnalyticsType.INCOME,
                DealerListIncome(
                    it.lastMonthList,
                    "One Month Income",
                    false
                ),
                null,
                null
            )
        )

        dataList.add(
            DashboardAnalytics(
                6,
                "#00796B",
                R.drawable.active_dealer,
                "Active Dealer",
                (it.activeDealer).toString(),
                DashboardAnalyticsType.DEALER_LIST,
                null,
                "active",
                null
            )
        )

        dataList.add(
            DashboardAnalytics(
                7,
                "#795548",
                R.drawable.deactive_dealer,
                "De-active Dealer",
                (it.deactiveDealer).toString(),
                DashboardAnalyticsType.DEALER_LIST,
                null,
                "deactive",
                null
            )
        )

        dataList.add(
            DashboardAnalytics(
                8,
                "#607D8B",
                R.drawable.my_dealer,
                "My Dealer",
                (it.activeDealer + it.deactiveDealer).toString(),
                DashboardAnalyticsType.DEALER_LIST,
                null,
                "all",
                null
            )
        )

        dataList.add(
            DashboardAnalytics(
                9,
                "#0288D1",
                R.drawable.money_withdrawal,
                "Withdrawal Income",
                (if (it.withdrawalData != null) it.withdrawalData?.withdrawalAmount else 0).toString(),
                DashboardAnalyticsType.WITHDRAWAL,
                null,
                null,
                it.withdrawalData
            )
        )

        dataList.add(
            DashboardAnalytics(
                10,
                "#303F9F",
                R.drawable.total_dealer,
                "Number of dealer",
                (it.allDealerCount).toString(),
                DashboardAnalyticsType.NONE, null, null, null
            )
        )
        dataList
    }

    return dashboardData
}