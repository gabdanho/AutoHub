package com.example.autohub.presentation.model.user

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class User(
    val firstName: String = "",
    val secondName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val image: String = "",
    val status: UserStatus = UserStatus.Offline,
    val localToken: String = ""
)