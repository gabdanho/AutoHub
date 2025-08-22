package com.example.autohub.presentation.model.user

data class User(
    val firstName: String = "",
    val secondName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val image: String = "",
    val status: UserStatus = UserStatus.Offline,
    val localToken: String = "",
    val uid: String = ""
)