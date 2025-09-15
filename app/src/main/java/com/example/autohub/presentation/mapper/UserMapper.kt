package com.example.autohub.presentation.mapper

import com.example.autohub.presentation.model.user.User
import com.example.autohub.domain.model.user.User as UserDomain

fun User.toUserDomain(): UserDomain {
    return UserDomain(
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        city = city,
        image = image,
        status = status.toUserStatusDomain(),
        localToken = localToken,
        uid = uid
    )
}

fun UserDomain.toUserPresentation(): User {
    return User(
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        city = city,
        image = image,
        status = status.toUserStatusPresentation(),
        localToken = localToken,
        uid = uid
    )
}