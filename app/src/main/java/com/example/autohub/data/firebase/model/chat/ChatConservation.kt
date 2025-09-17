package com.example.autohub.data.firebase.model.chat

data class ChatConservation(
    val lastMessage: String = "",
    val timeMillis: Long = 0L,
    val userId: String = "",
    val name: String = "",
    val imageUrl: String = ""
)