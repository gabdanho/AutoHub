package com.example.autohub.presentation.mapper

import com.example.autohub.presentation.model.user.User
import com.example.autohub.domain.model.user.User as UserDomain

fun User.toUserDomain(): UserDomain {
    return UserDomain(
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        phoneNumber = this.phoneNumber,
        city = this.city,
        image = this.image,
        status = this.status.toUserStatusDomain(),
        localToken = this.localToken,
        uid = this.uid
    )
}

fun UserDomain.toUserPresentation(): User {
    return User(
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        phoneNumber = this.phoneNumber,
        city = this.city,
        image = this.image,
        status = this.status.toUserStatusPresentation(),
        localToken = this.localToken,
        uid = this.uid
    )
}