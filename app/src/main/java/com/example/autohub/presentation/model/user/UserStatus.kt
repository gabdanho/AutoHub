package com.example.autohub.presentation.model.user

sealed class UserStatus {
    data object Online : UserStatus()
    data object Offline : UserStatus()
}