package com.technoecorp.gorilladealer.ui.mydealer

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.technoecorp.domain.domainmodel.response.dealer.citylist.CityWiseCount
import com.technoecorp.gorilladealer.R
import com.technoecorp.gorilladealer.databinding.CityCountListItemBinding
import java.util.*

class CityWiseCountAdapter(
    context: Context,
    private var callback: (CityWiseCount, Int, Boolean) -> Unit
) :
    ListAdapter<CityWiseCount, CityWiseCountAdapter.CityViewHolder>(CityDiffUtils()) {
    private var androidColors: IntArray
    private var activity: Activity? = null
    private var randomObj = Random()
    var stateId: Int = -1

    fun submitStateId(stateId: Int) {
        this.stateId = stateId
    }

    init {
        activity = context as Activity?
        this.androidColors = activity?.resources?.getIntArray(R.array.androidcolors)!!
    }


    inner class CityViewHolder(private val binding: CityCountListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cityWiseCount: CityWiseCount) {
            binding.locationText.text = cityWiseCount.cityName
            binding.activeUserText.text = cityWiseCount.activeCount.toString()
            binding.deactiveUserText.text = cityWiseCount.deactiveCount.toString()

            val random = randomObj.nextInt(androidColors.size)
            binding.ivAvatar.text = cityWiseCount.cityName.substring(0, 1)
            binding.ivAvatar.solidColor = androidColors[random]

            binding.deactiveUserBtn.setOnClickListener {
                callback(cityWiseCount, stateId, false)
            }
            binding.activeUserBtn.setOnClickListener {
                callback(cityWiseCount, stateId, true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val layout = LayoutInflater.from(parent.context)
        val binding = CityCountListItemBinding.inflate(layout, parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CityDiffUtils : DiffUtil.ItemCallback<CityWiseCount>() {
    override fun areItemsTheSame(oldItem: CityWiseCount, newItem: CityWiseCount): Boolean {
        return oldItem.cityId == newItem.cityId
    }

    override fun areContentsTheSame(oldItem: CityWiseCount, newItem: CityWiseCount): Boolean {
        return oldItem == newItem
    }
}

