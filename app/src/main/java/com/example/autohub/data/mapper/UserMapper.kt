package com.example.autohub.data.mapper

import com.example.autohub.data.firebase.model.user.User
import com.example.autohub.data.firebase.model.user.UserStatus
import com.example.autohub.domain.model.user.User as UserDomain

fun User.toUserDomain(): UserDomain {
    return UserDomain(
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        city = city,
        imageUrl = imageUrl,
        status = runCatching { UserStatus.fromValue(status) }
            .getOrDefault(UserStatus.OFFLINE)
            .toUserStatusDomain(),
        localToken = localToken,
        userId = uid
    )
}

fun UserDomain.toUserData(): User {
    return User(
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        city = city,
        imageUrl = imageUrl,
        status = status.toUserStatusData().value,
        localToken = localToken,
        uid = userId
    )
}