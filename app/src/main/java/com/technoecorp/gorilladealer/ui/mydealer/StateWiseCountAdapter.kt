package com.technoecorp.gorilladealer.ui.mydealer

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.technoecorp.domain.domainmodel.response.dealer.statelist.StateWiseCount
import com.technoecorp.gorilladealer.databinding.StateListItemBinding

class StateWiseCountAdapter(private val callback: (StateWiseCount) -> Unit) :
    ListAdapter<StateWiseCount, StateWiseCountAdapter.StateViewHolder>(StateDiffUtils()) {
    var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = StateListItemBinding.inflate(layoutInflater, parent, false)
        return StateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class StateViewHolder(private val binding: StateListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(stateWiseCount: StateWiseCount, position: Int) {
            binding.stateText.text = stateWiseCount.stateName
            binding.countText.text = stateWiseCount.count.toString()
            binding.selectedLinear.visibility =
                if (position == selectedPosition) View.VISIBLE else View.INVISIBLE
            binding.materialCard.setOnClickListener {
                val previousSelected = selectedPosition
                selectedPosition = position
                notifyItemChanged(previousSelected)
                notifyItemChanged(selectedPosition)
                callback(stateWiseCount)
            }
            binding.materialCard.backgroundTintList = ColorStateList.valueOf(stateWiseCount.color)
            //          binding.materialCard.setBackgroundColor(stateWiseCount.color)
        }
    }

}

class StateDiffUtils : DiffUtil.ItemCallback<StateWiseCount>() {
    override fun areItemsTheSame(oldItem: StateWiseCount, newItem: StateWiseCount): Boolean {
        return oldItem.stateId == newItem.stateId
    }

    override fun areContentsTheSame(oldItem: StateWiseCount, newItem: StateWiseCount): Boolean {
        return oldItem == newItem
    }
}

