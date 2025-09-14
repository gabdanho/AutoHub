package com.example.autohub.data.mapper

import com.example.autohub.data.firebase.model.user.User
import com.example.autohub.data.firebase.model.user.UserStatus
import com.example.autohub.domain.model.user.User as UserDomain

fun User.toUserDomain(): UserDomain {
    return UserDomain(
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        phoneNumber = this.phoneNumber,
        city = this.city,
        image = this.image,
        status = UserStatus.fromValue(this.status).toUserStatusDomain(),
        localToken = this.localToken,
        uid = this.uid
    )
}

fun UserDomain.toUserData(): User {
    return User(
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        phoneNumber = this.phoneNumber,
        city = this.city,
        image = this.image,
        status = this.status.toUserStatusData().value,
        localToken = this.localToken,
        uid = this.uid
    )
}