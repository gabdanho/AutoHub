package com.example.autohub.presentation.model.user

import kotlinx.serialization.Serializable

@Serializable
sealed class UserStatus {
    data object Online : UserStatus()
    data object Offline : UserStatus()
}