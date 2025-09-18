package com.example.autohub.data.firebase.model.chat

/**
 * Сообщение в чате.
 *
 * @param id ID сообщения
 * @param senderId ID отправителя
 * @param receiverId ID получателя
 * @param text Текст сообщения
 * @param timeMillis Время отправки
 * @param read Прочитано
 */
data class Message(
    val id: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val text: String = "",
    val timeMillis: Long = 0L,
    val read: Boolean = false
)