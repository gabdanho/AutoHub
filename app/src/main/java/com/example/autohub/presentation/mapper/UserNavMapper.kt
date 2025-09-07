package com.example.autohub.presentation.mapper

import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.navigation.model.nav_type.UserNav

fun User.toUserNav(): UserNav {
    return UserNav(
        uid = uid,
        image = image,
        firstName = firstName,
        lastName = secondName,
        city = city,
        phone = phoneNumber
    )
}