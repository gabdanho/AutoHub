package com.example.autohub.data.model.user

data class User(
    val firstName: String = "",
    val secondName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val image: String = "",
    val status: String = UserStatus.OFFLINE.name
)