package com.example.musicapp.presentation.model

data class MyTicket(
    val id: String,
    val eventName: String,
    val eventDate: String,
    val eventLocation: String,
    val ticketCode: String,
    val price: Long,
    val status: TicketStatus,
    val imageUrl: String
)

enum class TicketStatus {
    UPCOMING,
    USED,
    CANCELLED
}
