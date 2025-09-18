package com.example.autohub.presentation.model.messenger

/**
 * Модель сообщения для Presentation слоя.
 *
 * @property id ID сообщения
 * @property senderId ID отправителя
 * @property receiverId ID получателя
 * @property text Текст сообщения
 * @property timeFormatted Время сообщения в удобочитаемом формате
 * @property timeMillis Время сообщения в миллисекундах
 * @property isRead Статус прочтения
 */
data class Message(
    val id: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val text: String = "",
    val timeFormatted: String = "",
    val timeMillis: Long = 0L,
    val isRead: Boolean = false
)