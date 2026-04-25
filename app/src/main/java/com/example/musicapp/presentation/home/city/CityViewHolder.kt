package com.example.musicapp.presentation.home.city

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.data.model.City
import com.example.musicapp.databinding.ItemCityBinding

class CityViewHolder(
    private val binding: ItemCityBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: City) {
        binding.tvTitle.text = item.name

        Glide.with(binding.ivImage.context)
            .load(item.image)
            .into(binding.ivImage)
    }
}