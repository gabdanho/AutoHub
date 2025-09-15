package com.example.autohub.presentation.model.messenger

data class Message(
    val id: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val text: String = "",
    val timeFormatted: String = "",
    val timeMillis: Long = 0L,
    val isRead: Boolean = false
)