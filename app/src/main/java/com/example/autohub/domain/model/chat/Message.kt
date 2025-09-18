package com.example.autohub.domain.model.chat

/**
 * Модель сообщения в чате.
 *
 * @property id Идентификатор сообщения.
 * @property senderId Идентификатор отправителя.
 * @property receiverId Идентификатор получателя.
 * @property text Текст сообщения.
 * @property timeMillis Время отправки сообщения в миллисекундах.
 * @property isRead Прочитано ли сообщение.
 */
data class Message(
    val id: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val text: String = "",
    val timeMillis: Long = 0L,
    val isRead: Boolean = false,
)