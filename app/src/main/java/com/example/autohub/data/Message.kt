package com.example.autohub.data

import com.example.autohub.utils.getTime

data class Message(
    val sender: String = "",
    val receiver: String = "",
    val text: String = "",
    val time: String = getTime()
)