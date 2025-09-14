package com.example.autohub.domain.model.chat

sealed class UserStatus {

    data object Online: UserStatus()

    data object Offline: UserStatus()
}
