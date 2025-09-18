package com.example.autohub.presentation.navigation.model.nav_type

import com.example.autohub.presentation.model.user.User
import kotlinx.serialization.KSerializer

/**
 * [NavType] для передачи объектов [User] между экранами.
 */
class UserNavType(serializer: KSerializer<User> = User.serializer())
    : NavTypeSerializer<User>(
    serializer = serializer
)