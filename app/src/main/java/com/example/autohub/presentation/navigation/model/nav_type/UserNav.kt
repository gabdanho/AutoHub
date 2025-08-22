package com.example.autohub.presentation.navigation.model.nav_type

import androidx.compose.runtime.Immutable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class UserNav(
    val uid: String = "",
    val image: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val city: String = "",
    val phone: String = ""
)

class UserNavType(serializer: KSerializer<UserNav> = UserNav.serializer())
    : NavTypeSerializer<UserNav>(
        serializer = serializer
    )