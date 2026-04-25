package com.example.musicapp.presentation.home.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemBannerVideoBinding
import com.example.musicapp.presentation.home.event.viewholder.BannerViewHolder

class BannerAdapter(
    private val videoIds: List<String>
) : RecyclerView.Adapter<BannerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = ItemBannerVideoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(videoIds[position])
    }

    override fun getItemCount(): Int = videoIds.size
}