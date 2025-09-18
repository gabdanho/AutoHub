package com.example.autohub.presentation.model.messenger

import com.example.autohub.presentation.model.user.UserStatus

/**
 * Статус чата для Presentation слоя.
 *
 * @property status Статус пользователя ([UserStatus])
 * @property countUnreadMessages Количество непрочитанных сообщений
 */
data class ChatStatus(
    val status: UserStatus? = null,
    val countUnreadMessages: Int = 0
)