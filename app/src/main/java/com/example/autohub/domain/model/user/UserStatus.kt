package com.example.autohub.domain.model.user

/**
 * Статус пользователя в системе.
 */
sealed class UserStatus {

    /** Пользователь в сети. */
    data object Online: UserStatus()

    /** Пользователь оффлайн. */
    data object Offline: UserStatus()
}