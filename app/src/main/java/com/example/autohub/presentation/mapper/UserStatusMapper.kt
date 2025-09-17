package com.example.autohub.presentation.mapper

import com.example.autohub.presentation.model.user.UserStatus
import com.example.autohub.domain.model.user.UserStatus as UserStatusDomain

fun UserStatusDomain.toUserStatusPresentation(): UserStatus {
    return when (this) {
        UserStatusDomain.Offline -> UserStatus.Offline
        UserStatusDomain.Online -> UserStatus.Online
    }
}

fun UserStatus.toUserStatusDomain(): UserStatusDomain {
    return when (this) {
        UserStatus.Offline -> UserStatusDomain.Offline
        UserStatus.Online -> UserStatusDomain.Online
    }
}