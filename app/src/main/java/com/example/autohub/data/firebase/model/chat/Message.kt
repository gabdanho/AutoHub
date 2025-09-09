package com.example.autohub.data.firebase.model.chat

data class Message(
    val id: String = "",
    val senderUID: String = "",
    val receiverUID: String = "",
    val text: String = "",
    val timeMillis: Long = 0L,
    val formattedData: String = "",
    val read: Boolean = false
)