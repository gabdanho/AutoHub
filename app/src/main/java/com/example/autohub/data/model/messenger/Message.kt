package com.example.autohub.data.model.messenger

import com.example.autohub.utils.getTime

data class Message(
    val id: String = "",
    val sender: String = "",
    val receiver: String = "",
    val text: String = "",
    val time: String = getTime(),
    val read: Boolean = false
)