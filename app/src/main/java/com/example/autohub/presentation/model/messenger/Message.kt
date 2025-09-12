package com.example.autohub.presentation.model.messenger

data class Message(
    val id: String = "",
    val senderUid: String = "",
    val receiverUID: String = "",
    val text: String = "",
    val formattedData: String = "",
    val timeMillis: Long = 0L,
    val isRead: Boolean = false
)