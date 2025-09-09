package com.example.autohub.presentation.model.messenger

data class Message(
    val id: String = "",
    val senderUid: String = "",
    val receiverUID: String = "",
    val text: String = "",
    val timeFormatted: String = "",
    val timeMillis: Long = 0L,
    val read: Boolean = false
)