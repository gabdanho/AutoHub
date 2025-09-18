package com.example.autohub.presentation.model.user

import kotlinx.serialization.Serializable

/**
 * Статус пользователя.
 */
@Serializable
sealed class UserStatus {

    /**
     * Пользователь онлайн
     */
    @Serializable
    data object Online : UserStatus()

    /**
     * Пользователь оффлайн
     */
    @Serializable
    data object Offline : UserStatus()
}