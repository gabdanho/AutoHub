package com.example.autohub.data.firebase.model.user

data class User(
    val firstName: String = "",
    val secondName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val image: String = "",
    val status: UserStatus = UserStatus.OFFLINE,
    val localToken: String = ""
)
