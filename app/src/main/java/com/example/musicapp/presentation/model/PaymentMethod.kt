package com.example.musicapp.presentation.model

data class PaymentMethod(
    val id: String,
    val name: String,
    val iconRes: Int,
    var isSelected: Boolean = false,
    val subIcons: List<Int> = emptyList()
)