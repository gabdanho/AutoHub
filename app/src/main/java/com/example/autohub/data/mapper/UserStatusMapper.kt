package com.example.autohub.data.mapper

import com.example.autohub.data.firebase.model.user.UserStatus
import com.example.autohub.domain.model.UserStatus as UserStatusDomain

fun UserStatusDomain.toUserStatusData(): UserStatus {
    return when (this) {
        UserStatusDomain.Offline -> UserStatus.OFFLINE
        UserStatusDomain.Online -> UserStatus.ONLINE
    }
}

fun UserStatus.toUserStatusDomain(): UserStatusDomain {
    return when (this) {
        UserStatus.OFFLINE -> UserStatusDomain.Offline
        UserStatus.ONLINE -> UserStatusDomain.Online
    }
}