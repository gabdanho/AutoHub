package com.example.autohub.domain.model.user

data class User(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val imageUrl: String = "",
    val status: UserStatus = UserStatus.Offline,
    val localToken: String = "",
    val userId: String = ""
)