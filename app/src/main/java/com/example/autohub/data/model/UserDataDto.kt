package com.example.autohub.data.model

data class UserDataDto(
    val firstName: String = "",
    val secondName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val image: String = "",
    val status: UserStatusDto = UserStatusDto.Offline,
    val localToken: String = ""
)
