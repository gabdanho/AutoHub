package com.example.autohub.data.firebase.model.user

private const val ONLINE_VALUE = "ONLINE"
private const val OFFLINE_VALUE = "OFFLINE"

enum class UserStatus(val value: String) {
    ONLINE(value = ONLINE_VALUE),
    OFFLINE(value = OFFLINE_VALUE);

    companion object {
        fun fromValue(value: String): UserStatus {
            return when (value.uppercase()) {
                ONLINE_VALUE -> ONLINE
                OFFLINE_VALUE -> OFFLINE
                else -> throw IllegalArgumentException("Unknown user status: $value")
            }
        }
    }
}