package com.example.musicapp.presentation.home.event.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.databinding.ItemTrendingEventBinding

class TrendingViewHolder(val binding: ItemTrendingEventBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(imageUrl: String, position: Int) {
        binding.tvRank.text = (position + 1).toString()
        Glide.with(binding.ivTrendingThumb.context)
            .load(imageUrl)
            .into(binding.ivTrendingThumb)
    }
}