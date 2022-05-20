package com.technoecorp.gorilladealer.ui.income

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.technoecorp.domain.domainmodel.data.Dealer
import com.technoecorp.gorilladealer.R
import com.technoecorp.gorilladealer.databinding.DealerListItemBinding
import java.util.*

class IncomeListAdapter(private val context: Context) :
    ListAdapter<Dealer, IncomeListAdapter.IncomeViewHolder>(IncomeDiffUtils()) {
    var androidColors: IntArray
    var activity: Activity? = null
    var randomObj = Random()

    init {
        activity = context as Activity?
        this.androidColors = activity?.resources?.getIntArray(R.array.androidcolors)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DealerListItemBinding.inflate(layoutInflater, parent, false)
        return IncomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class IncomeViewHolder(private var dealerListItemBinding: DealerListItemBinding) :
        RecyclerView.ViewHolder(dealerListItemBinding.root) {
        fun bind(dealer: Dealer) {
            dealerListItemBinding.nameText.text = dealer.name
            dealerListItemBinding.phoneText.text = dealer.mobileNo
            val state = dealer.state?.stateName ?: "NA"
            val city = dealer.city?.cityName ?: "NA"
            dealerListItemBinding.locationText.text = "$city $state"

            val random = randomObj.nextInt(androidColors.size)
            dealerListItemBinding.ivAvatar.text = dealer.name.substring(0, 1)
            dealerListItemBinding.ivAvatar.solidColor = androidColors[random]
        }
    }

}

class IncomeDiffUtils : DiffUtil.ItemCallback<Dealer>() {
    override fun areItemsTheSame(oldItem: Dealer, newItem: Dealer): Boolean {
        return oldItem.dealerId == newItem.dealerId
    }

    override fun areContentsTheSame(oldItem: Dealer, newItem: Dealer): Boolean {
        return oldItem == newItem
    }

}

