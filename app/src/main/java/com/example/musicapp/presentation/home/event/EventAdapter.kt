package com.example.musicapp.presentation.home.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemNormalEventsBinding
import com.example.musicapp.databinding.ItemSectionHeaderBinding
import com.example.musicapp.presentation.home.event.viewholder.HeaderViewHolder
import com.example.musicapp.presentation.home.event.viewholder.NormalEventViewHolder
import com.example.musicapp.presentation.model.HomeItem

class EventAdapter(
    private val onEventClick: (HomeItem.Event) -> Unit
) : ListAdapter<HomeItem, RecyclerView.ViewHolder>(HomeItemDiffCallback) {
    enum class ViewType(val id: Int) {
        HEADER(0),
        EVENT(1)
    }

    object HomeItemDiffCallback : DiffUtil.ItemCallback<HomeItem>() {
        override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HomeItem.Header -> ViewType.HEADER.id
            is HomeItem.Event -> ViewType.EVENT.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ViewType.HEADER.id -> HeaderViewHolder(
                ItemSectionHeaderBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            else -> NormalEventViewHolder(ItemNormalEventsBinding.inflate(inflater, parent, false)) { item ->
                onEventClick(item)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when {
            holder is HeaderViewHolder && item is HomeItem.Header -> holder.bind(item)
            holder is NormalEventViewHolder && item is HomeItem.Event -> holder.bind(item)
        }
    }
}