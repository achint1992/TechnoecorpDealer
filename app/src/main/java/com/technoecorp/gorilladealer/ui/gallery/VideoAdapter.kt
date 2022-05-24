package com.technoecorp.gorilladealer.ui.gallery

import android.content.Context
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
import com.technoecorp.gorilladealer.databinding.VideoGalleryItemBinding

class VideoAdapter(private var context: Context) :
    ListAdapter<Gallery, VideoAdapter.VideoViewHolder>(VideoDiffUtils()) {

    inner class VideoViewHolder(private var binding: VideoGalleryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gallery: Gallery) {
            Glide.with(context).load(
                "https://img.youtube.com/vi/" + gallery.videoId
                    .toString() + "/mqdefault.jpg"
            ).into(binding.image)

            binding.image.setOnClickListener {
                val bundle = bundleOf("urlLink" to gallery.url, "videoId" to gallery.videoId)
                it.findNavController()
                    .navigate(R.id.action_galleryFragment_to_youtubeActivity, bundle)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val layout = LayoutInflater.from(parent.context)
        val binding = VideoGalleryItemBinding.inflate(layout, parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class VideoDiffUtils : DiffUtil.ItemCallback<Gallery>() {
    override fun areItemsTheSame(oldItem: Gallery, newItem: Gallery): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Gallery, newItem: Gallery): Boolean {
        return oldItem == newItem
    }
}

