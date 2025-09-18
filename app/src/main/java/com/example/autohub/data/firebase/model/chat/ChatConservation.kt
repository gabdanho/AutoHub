package com.example.autohub.data.firebase.model.chat

/**
 * Консервация чата с последним сообщением.
 *
 * @param lastMessage Текст последнего сообщения
 * @param timeMillis Время последнего сообщения
 * @param userId ID пользователя
 * @param name Имя пользователя
 * @param imageUrl URL аватарки пользователя
 */
data class ChatConservation(
    val lastMessage: String = "",
    val timeMillis: Long = 0L,
    val userId: String = "",
    val name: String = "",
    val imageUrl: String = ""
)