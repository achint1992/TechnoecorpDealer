package com.technoecorp.gorilladealer.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.technoecorp.domain.domainmodel.response.auth.otp.GeoList
import com.technoecorp.gorilladealer.databinding.StateSelectedItemBinding

class AutoCompleteAdapter<T>(
    private val mContext: Context,
    resId: Int,
    textViewId: Int,
    private var list: ArrayList<T>
) :
    ArrayAdapter<T>(mContext, resId, textViewId) where T : GeoList {

    var inflate: LayoutInflater = LayoutInflater.from(context)
    private val items: ArrayList<T> get() = list
    private val itemsAll: ArrayList<T> get() = list

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): T {
        return items[position]
    }

    override fun getFilter(): Filter {
        return nameFilter
    }

    fun addItems(list: List<T>) {
        items.clear()
        itemsAll.clear()
        items.addAll(list)
        itemsAll.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: StateSelectedItemBinding
        if (convertView == null) {
            binding = StateSelectedItemBinding.inflate(inflate, parent, false)
            convertView?.tag = binding
        } else {
            binding = StateSelectedItemBinding.bind(convertView)
        }
        binding.stateName.text =
            items[position].getDisplayName()


        return binding.root

    }

    private var nameFilter: Filter = object : Filter() {
        override fun convertResultToString(resultValue: Any): String {
            return (resultValue as T).getCompareValue()
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            return run {
                val filterResults = FilterResults()
                constraint?.let {
                    if (constraint.toString() != "") {
                        val suggestions = itemsAll.filter {
                            it.getCompareValue().lowercase().startsWith(constraint.toString(), true)
                        }
                        filterResults.values = suggestions
                        filterResults.count = suggestions.size
                    }
                }
                filterResults
            }
        }

        override fun publishResults(
            constraint: CharSequence?,
            results: FilterResults
        ) {
            constraint?.let {
                if (constraint.toString() != "") {
                    val filteredList: ArrayList<T> =
                        results.values as ArrayList<T>
                    if (results.count > 0) {
                        items.clear()
                        items.addAll(filteredList)
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }


}