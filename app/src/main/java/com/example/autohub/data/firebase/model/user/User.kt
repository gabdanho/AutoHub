package com.example.autohub.data.firebase.model.user

data class User(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val imageUrl: String = "",
    val status: String = "",
    val localToken: String = "",
    val uid: String = ""
)
