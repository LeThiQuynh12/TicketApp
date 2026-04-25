package com.example.musicapp.presentation.home.event.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.databinding.ItemNormalEventsBinding
import com.example.musicapp.presentation.model.HomeItem

class NormalEventViewHolder(
    private val binding: ItemNormalEventsBinding,
    private val onEventClick: (HomeItem.Event) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(event: HomeItem.Event) {
        Glide.with(binding.ivEventThumb.context)
            .load(event.imageUrl)
            .into(binding.ivEventThumb)

        binding.tvEventName.text = event.name
        binding.tvEventPrice.text = event.price
        binding.tvEventDate.text = event.date

        itemView.setOnClickListener {
            onEventClick(event)
        }
    }
}