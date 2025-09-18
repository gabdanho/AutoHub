package com.example.autohub.data.mapper

import com.example.autohub.data.firebase.model.user.UserStatus
import com.example.autohub.domain.model.user.UserStatus as UserStatusDomain

/**
 * Конвертация [UserStatusDomain] в [UserStatus].
 *
 * @receiver Domain-статус пользователя
 * @return Data-статус пользователя
 */
fun UserStatusDomain.toUserStatusData(): UserStatus {
    return when (this) {
        UserStatusDomain.Offline -> UserStatus.OFFLINE
        UserStatusDomain.Online -> UserStatus.ONLINE
    }
}

/**
 * Конвертация [UserStatus] в [UserStatusDomain].
 *
 * @receiver Data-статус пользователя
 * @return Domain-статус пользователя
 */
fun UserStatus.toUserStatusDomain(): UserStatusDomain {
    return when (this) {
        UserStatus.OFFLINE -> UserStatusDomain.Offline
        UserStatus.ONLINE -> UserStatusDomain.Online
    }
}