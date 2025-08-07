package com.example.autohub.data.firebase.model.chat

data class SendMessageDto(
    val to: String?,
    val notification: NotificationBody
)

data class NotificationBody(
    val name: String,
    val body: String
)