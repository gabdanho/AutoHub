package com.example.autohub.presentation.model.messenger

import com.example.autohub.data.utils.getTimeString

data class Message(
    val id: String = "",
    val sender: String = "",
    val receiver: String = "",
    val text: String = "",
    val time: String = getTimeString(),
    val timeMillis: Long = System.currentTimeMillis(),
    val read: Boolean = false
)