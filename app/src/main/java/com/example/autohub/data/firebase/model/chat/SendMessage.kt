package com.example.autohub.data.firebase.model.chat

/**
 * DTO для отправки сообщения.
 *
 * @param to ID получателя
 * @param notification Тело уведомления
 */
data class SendMessageDto(
    val to: String?,
    val notification: NotificationBody
)

/**
 * Тело уведомления.
 *
 * @param name Имя отправителя
 * @param body Текст уведомления
 */
data class NotificationBody(
    val name: String,
    val body: String
)