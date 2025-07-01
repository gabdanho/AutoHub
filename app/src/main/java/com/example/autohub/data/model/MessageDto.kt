package com.example.autohub.data.model

data class MessageDto(
    val id: String = "",
    val senderUID: String = "",
    val receiverUID: String = "",
    val text: String = "",
    val timeMillis: Long = 0L,
    val read: Boolean = false
)