package com.example.autohub.domain.model

sealed class UserStatus {

    data object Online: UserStatus()

    data object Offline: UserStatus()
}
