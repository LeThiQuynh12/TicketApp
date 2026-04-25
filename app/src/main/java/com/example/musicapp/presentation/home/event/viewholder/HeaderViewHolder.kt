package com.example.musicapp.presentation.home.event.viewholder

import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.databinding.ItemSectionHeaderBinding
import com.example.musicapp.presentation.model.HomeItem

class HeaderViewHolder(private val binding: ItemSectionHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(header: HomeItem.Header) {
        binding.tvSectionTitle.text = header.title
        binding.btnSeeMore.setOnClickListener {
            // Handle click on "See More" button
        }
    }
}