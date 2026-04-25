package com.example.musicapp.presentation.home.banner

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.databinding.ItemEventCardBinding

class SpecialEventsViewHolder(val binding: ItemEventCardBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(imageUrl: String) {
        Glide.with(binding.ivEventThumb.context)
            .load(imageUrl)
            .into(binding.ivEventThumb)
    }
}