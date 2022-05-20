package com.technoecorp.gorilladealer.ui.gallery

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.technoecorp.domain.domainmodel.response.company.gallery.Gallery
import com.technoecorp.gorilladealer.R
import com.technoecorp.gorilladealer.databinding.PhotoPdfGalleryItemBinding

class PhotoPdfAdapter(private var context: Context) :
    ListAdapter<Gallery, PhotoPdfAdapter.PhotoViewHolder>(PhotoDiffUtils()) {

    inner class PhotoViewHolder(private var binding: PhotoPdfGalleryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gallery: Gallery) {
            when (gallery.type) {
                "photo" -> {
                    Glide.with(context).load(gallery.url).into(binding.iconImage)
                }
                "pdf" -> {
                    binding.iconImage.setImageResource(R.drawable.pdf_icon)
                }
            }
            binding.fileTitle.text = gallery.title
            binding.iconImage.setOnClickListener {
                when (gallery.type) {
                    "photo" -> {
                        val bundle = bundleOf("url" to gallery.url)
                        it.findNavController()
                            .navigate(R.id.action_galleryFragment_to_imageFullActivity, bundle)
                    }
                    "pdf" -> {
                        val bundle = bundleOf("url" to gallery.url)
                        it.findNavController()
                            .navigate(R.id.action_galleryFragment_to_pdfActivity, bundle)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val layout = LayoutInflater.from(parent.context)
        val binding = PhotoPdfGalleryItemBinding.inflate(layout, parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PhotoDiffUtils : DiffUtil.ItemCallback<Gallery>() {
    override fun areItemsTheSame(oldItem: Gallery, newItem: Gallery): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Gallery, newItem: Gallery): Boolean {
        return oldItem == newItem
    }
}

