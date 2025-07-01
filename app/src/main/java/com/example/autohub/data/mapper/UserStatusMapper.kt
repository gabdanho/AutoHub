package com.example.autohub.data.mapper

import com.example.autohub.data.model.UserStatusDto
import com.example.autohub.domain.model.UserStatus

fun UserStatus.toUserStatusDto(): UserStatusDto {
    return when (this) {
        UserStatus.Offline -> UserStatusDto.OFFLINE
        UserStatus.Online -> UserStatusDto.ONLINE
    }
}

fun UserStatusDto.toUserStatus(): UserStatus {
    return when (this) {
        UserStatusDto.OFFLINE -> UserStatus.Offline
        UserStatusDto.ONLINE -> UserStatus.Online
    }
}