package com.example.musicapp.presentation.home.event.viewholder

import android.R
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.databinding.ItemBannerVideoBinding

class BannerViewHolder(
    private val binding: ItemBannerVideoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(videoId: String) {
        // 1. Tạo URL lấy ảnh thumbnail chất lượng cao từ YouTube
        val thumbnailUrl = "https://img.youtube.com/vi/$videoId/maxresdefault.jpg"

        // 2. Dùng Glide để load ảnh
        Glide.with(binding.root.context)
            .load(thumbnailUrl)
            .placeholder(R.color.darker_gray) // Ảnh hiển thị khi đang tải
            .error("https://img.youtube.com/vi/$videoId/hqdefault.jpg") // Fallback nếu ảnh maxres ko có
            .into(binding.ivBannerThumbnail)

        // 3. Xử lý sự kiện bấm nút "Xem chi tiết"
        binding.btnDetail.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoId"))
            binding.root.context.startActivity(intent)
        }

        // Hoặc cho phép bấm vào cả tấm hình để mở video
        binding.root.setOnClickListener {
            binding.btnDetail.performClick()
        }
    }
}