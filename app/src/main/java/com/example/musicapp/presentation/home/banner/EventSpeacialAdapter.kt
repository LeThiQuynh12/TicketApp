package com.example.musicapp.presentation.home.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemEventCardBinding
import com.example.musicapp.databinding.ItemTrendingEventBinding
import com.example.musicapp.presentation.home.event.viewholder.TrendingViewHolder

class EventSpeacialAdapter(
    private val images: List<String>,
    private val isTrending: Boolean = false
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class EventViewType(val id: Int) {
        EVENT(0),
        TRENDING(1)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isTrending) EventViewType.TRENDING.id else EventViewType.EVENT.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            EventViewType.TRENDING.id -> TrendingViewHolder(
                ItemTrendingEventBinding.inflate(inflater, parent, false)
            )

            else -> SpecialEventsViewHolder(
                ItemEventCardBinding.inflate(inflater, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val imageUrl = images[position]

        when (holder) {
            is SpecialEventsViewHolder -> holder.bind(imageUrl)
            is TrendingViewHolder -> holder.bind(imageUrl, position)
        }
    }

    override fun getItemCount() = images.size
}