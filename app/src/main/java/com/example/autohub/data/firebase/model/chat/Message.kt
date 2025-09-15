package com.example.autohub.data.firebase.model.chat

data class Message(
    val id: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val text: String = "",
    val timeMillis: Long = 0L,
    val read: Boolean = false
)