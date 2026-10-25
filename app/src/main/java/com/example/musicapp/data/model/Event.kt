package com.example.musicapp.data.model

data class Event(
    val id: String,
    val imageUrl: String,
    val name: String,
    val price: String,
    val date: String,
    val description: String,
    val location: Location,
    val gallery: List<String>,
    val seatMapUrl: String,
    val organizerName: String,
    val contactPhone: String
)

data class Location(
    val name: String,
    val province: String,
    val district: String,
    val ward: String,
    val addressDetail: String
)

