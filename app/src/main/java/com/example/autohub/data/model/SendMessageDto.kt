package com.example.autohub.data.model

data class SendMessageDto(
    val to: String?,
    val notification: NotificationBody
)

data class NotificationBody(
    val name: String,
    val body: String
)