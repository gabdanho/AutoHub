package com.example.autohub.data.firebase.model.user

enum class UserStatus(val value: String) {
    ONLINE(value = "ONLINE"),
    OFFLINE(value = "OFFLINE");

    companion object {
        fun fromValue(value: String): UserStatus {
            return when (value.uppercase()) {
                "ONLINE" -> ONLINE
                "OFFLINE" -> OFFLINE
                else -> throw IllegalArgumentException("Unknown user status: '$value'")
            }
        }
    }
}