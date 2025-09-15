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
        image = image,
        status = UserStatus.fromValue(status).toUserStatusDomain(),
        localToken = localToken,
        uid = uid
    )
}

fun UserDomain.toUserData(): User {
    return User(
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        city = city,
        image = image,
        status = status.toUserStatusData().value,
        localToken = localToken,
        uid = uid
    )
}