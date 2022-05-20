package com.technoecorp.gorilladealer.ui.dashboard

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.technoecorp.domain.domainmodel.response.DashboardAnalytics
import com.technoecorp.domain.domainmodel.response.DashboardAnalyticsType
import com.technoecorp.gorilladealer.R
import com.technoecorp.gorilladealer.databinding.ItemAnalyticsBinding


class DashboardAdapter(
    private var context: Context,
    private var showIncomeList: (dashboardAnalytics: DashboardAnalytics) -> Unit,
    private var showDealerList: () -> Unit,
    private var requestWithdrawalRequest: () -> Unit
) :
    ListAdapter<DashboardAnalytics, DashboardAdapter.DashboardViewHolder>(DashboardDiffUtils()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAnalyticsBinding.inflate(layoutInflater, parent, false)
        return DashboardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DashboardViewHolder(private var itemAnalyticsBinding: ItemAnalyticsBinding) :
        RecyclerView.ViewHolder(itemAnalyticsBinding.root) {
        fun bind(dashboardAnalytics: DashboardAnalytics) {
            itemAnalyticsBinding.cardView.background.setTint(Color.parseColor(dashboardAnalytics.colorCodeCard))
            itemAnalyticsBinding.totalIcon.setImageResource(dashboardAnalytics.icon)
            itemAnalyticsBinding.totalTitle.text = dashboardAnalytics.textHeader
            itemAnalyticsBinding.totalValue.text = dashboardAnalytics.value

            when (dashboardAnalytics.type) {
                DashboardAnalyticsType.WITHDRAWAL -> {
                    if (dashboardAnalytics.withdrawalData != null) {
                        if (dashboardAnalytics.withdrawalData?.count!! > 1) {
                            itemAnalyticsBinding.buttonAction.visibility = View.VISIBLE
                            itemAnalyticsBinding.buttonAction.setOnClickListener {
                                //call for withdrawal API call
                                requestWithdrawalRequest()
                            }
                        } else {
                            itemAnalyticsBinding.buttonAction.setOnClickListener(null)
                            itemAnalyticsBinding.buttonAction.visibility = View.GONE
                        }
                    } else {
                        itemAnalyticsBinding.buttonAction.setOnClickListener(null)
                        itemAnalyticsBinding.buttonAction.visibility = View.GONE
                    }
                    itemAnalyticsBinding.buttonAction.text =
                        context.getString(R.string.withdrawal_request)
                    itemAnalyticsBinding.cardView.setOnClickListener(null)
                }
                DashboardAnalyticsType.INCOME -> {
                    itemAnalyticsBinding.cardView.setOnClickListener {
                        showIncomeList(dashboardAnalytics)
                    }
                    itemAnalyticsBinding.buttonAction.visibility = View.GONE
                }
                DashboardAnalyticsType.DEALER_LIST -> {
                    itemAnalyticsBinding.cardView.setOnClickListener {
                        showDealerList()
                    }
                    itemAnalyticsBinding.buttonAction.visibility = View.GONE

                }
                DashboardAnalyticsType.NONE -> {
                    itemAnalyticsBinding.cardView.setOnClickListener(null)
                    itemAnalyticsBinding.buttonAction.visibility = View.GONE
                }
            }
        }
    }
}

class DashboardDiffUtils : DiffUtil.ItemCallback<DashboardAnalytics>() {
    override fun areItemsTheSame(
        oldItem: DashboardAnalytics,
        newItem: DashboardAnalytics
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: DashboardAnalytics,
        newItem: DashboardAnalytics
    ): Boolean {
        return oldItem == newItem
    }

}