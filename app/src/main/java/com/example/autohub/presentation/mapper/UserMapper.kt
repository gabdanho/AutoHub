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
        imageUrl = imageUrl,
        status = status.toUserStatusDomain(),
        localToken = localToken,
        userId = uid
    )
}

fun UserDomain.toUserPresentation(): User {
    return User(
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        city = city,
        imageUrl = imageUrl,
        status = status.toUserStatusPresentation(),
        localToken = localToken,
        uid = userId
    )
}