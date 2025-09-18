package com.example.autohub.presentation.model.messenger

/**
 * Модель переписки (чата) для Presentation слоя.
 *
 * @property lastMessage Последнее сообщение
 * @property timeMillis Время последнего сообщения в миллисекундах (строка)
 * @property uid ID пользователя-собеседника
 * @property name Имя пользователя
 * @property imageUrl URL аватарки пользователя
 */
data class ChatConservation(
    val lastMessage: String = "",
    val timeMillis: String = "",
    val uid: String = "",
    val name: String = "",
    val imageUrl: String = ""
)