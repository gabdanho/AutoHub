package com.example.autohub.domain.model

data class Message(
    val id: String = "",
    val senderUID: String = "",
    val receiverUID: String = "",
    val text: String = "",
    val timeMillis: Long = 0L,
    val read: Boolean = false
)