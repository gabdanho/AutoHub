package com.example.autohub.presentation.model.user

import kotlinx.serialization.Serializable

@Serializable
sealed class UserStatus {
    @Serializable
    data object Online : UserStatus()

    @Serializable
    data object Offline : UserStatus()
}