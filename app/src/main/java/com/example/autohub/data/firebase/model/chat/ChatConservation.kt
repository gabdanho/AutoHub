package com.example.autohub.data.firebase.model.chat

data class ChatConservation(
    val lastMessage: String = "",
    val timeMillis: Long = 0L,
    val uid: String = "",
    val name: String = "",
    val imageUrl: String = ""
)