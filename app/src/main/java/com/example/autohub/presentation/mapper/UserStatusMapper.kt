package com.example.autohub.presentation.mapper

import com.example.autohub.presentation.model.user.UserStatus
import com.example.autohub.domain.model.user.UserStatus as UserStatusDomain

/**
 * Конвертация [UserStatusDomain] в [UserStatus].
 *
 * @receiver Domain-статус пользователя
 * @return Presentation-статус пользователя
 */
fun UserStatusDomain.toUserStatusPresentation(): UserStatus {
    return when (this) {
        UserStatusDomain.Offline -> UserStatus.Offline
        UserStatusDomain.Online -> UserStatus.Online
    }
}

/**
 * Конвертация [UserStatus] в [UserStatusDomain].
 *
 * @receiver Presentation-статус пользователя
 * @return Domain-статус пользователя
 */
fun UserStatus.toUserStatusDomain(): UserStatusDomain {
    return when (this) {
        UserStatus.Offline -> UserStatusDomain.Offline
        UserStatus.Online -> UserStatusDomain.Online
    }
}