package com.example.autohub.domain.model

data class ChatConservation(
    val lastMessage: String = "",
    val time: Long = 0L,
    val uid: String = "",
    val name: String = "",
    val imageUrl: String = ""
)