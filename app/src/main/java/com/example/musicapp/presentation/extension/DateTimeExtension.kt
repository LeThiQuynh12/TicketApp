package com.example.musicapp.presentation.extension

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
fun formatTime(ms: Int): String {
    val minutes = (ms / 1000) / 60
    val seconds = (ms / 1000) % 60
    return String.format("%d:%02d", minutes, seconds)
}