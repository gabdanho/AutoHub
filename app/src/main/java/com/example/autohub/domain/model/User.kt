package com.example.autohub.domain.model

data class User(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val image: String = "",
    val status: UserStatus = UserStatus.Offline,
    val localToken: String = "",
    val uid: String = ""
)
