package com.example.musicapp.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ticket(
    val id: String,
    val name: String,
    val price: String,
    var quantity: Int = 0
) : Parcelable