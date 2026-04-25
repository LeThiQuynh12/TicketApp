package com.example.musicapp.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class HomeItem : Parcelable {
    data class Header(val id: String, val title: String) : HomeItem()

    @Parcelize
    data class Event(
        val id: String,
        val imageUrl: String,
        val category: String,
        val name: String,
        val price: String,
        val date: String,
        val description: String,
        val location: String,
        val gallery: List<String>,
        val seatMapUrl: String,
        val organizerName: String,
        val contactPhone: String
    ) : HomeItem()
}