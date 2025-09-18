package com.example.autohub.domain.model.chat

/**
 * Модель чата для отображения списка переписок.
 *
 * @property lastMessage Последнее сообщение в чате.
 * @property timeMillis Время последнего сообщения в миллисекундах.
 * @property userId Идентификатор участника чата.
 * @property name Имя участника чата.
 * @property imageUrl URL изображения участника.
 */
data class ChatConservation(
    val lastMessage: String = "",
    val timeMillis: Long = 0L,
    val userId: String = "",
    val name: String = "",
    val imageUrl: String = ""
)