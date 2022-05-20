package com.technoecorp.gorilladealer.ui.products

import android.content.ComponentCallbacks
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.technoecorp.domain.domainmodel.response.company.product.Package
import com.technoecorp.domain.domainmodel.response.company.product.Product
import com.technoecorp.gorilladealer.R
import com.technoecorp.gorilladealer.databinding.PackageListItemBinding
import com.technoecorp.gorilladealer.databinding.ProductListItemBinding

class PackageAdapter(private var context: Context,private var  callback: (Int, Int, String) -> Unit) :
    ListAdapter<Package, PackageAdapter.PackageViewHolder>(PackageDiffUtil()) {

    inner class PackageViewHolder(private var binding: PackageListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(packageX: Package) {
            binding.productName.text = packageX.packageName
            binding.buyButton.text = context.getString(R.string.buy_at, packageX.packagePrice)
            Glide.with(context).load(packageX.packageImg).apply(
                RequestOptions.diskCacheStrategyOf(
                    DiskCacheStrategy.NONE
                )
            ).into(binding.packageImage)

            binding.buyButton.setOnClickListener {
                callback(packageX.productId,packageX.packageId,packageX.packagePrice)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PackageListItemBinding.inflate(inflater, parent, false)
        return PackageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PackageDiffUtil : DiffUtil.ItemCallback<Package>() {
    override fun areItemsTheSame(oldItem: Package, newItem: Package): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: Package, newItem: Package): Boolean {
        return oldItem == newItem
    }

}