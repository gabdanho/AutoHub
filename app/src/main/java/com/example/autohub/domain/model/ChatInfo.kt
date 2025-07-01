package com.example.autohub.domain.model

data class ChatInfo(
    val name: String = "",
    val image: String = "",
    val lastMessage: String = "",
    val time: Long = 0L,
    val uid: String = ""
)