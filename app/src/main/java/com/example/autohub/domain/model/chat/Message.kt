package com.example.autohub.domain.model.chat

data class Message(
    val id: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val text: String = "",
    val timeMillis: Long = 0L,
    val isRead: Boolean = false,
)