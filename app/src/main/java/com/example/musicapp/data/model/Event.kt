package com.example.musicapp.data.model

data class Event(
    val id: String,
    val imageUrl: String,
    val name: String,
    val price: String,
    val date: String,
    val description: String,
    val location: String,
    val gallery: List<String>,
    val seatMapUrl: String,
    val organizerName: String,
    val contactPhone: String
)