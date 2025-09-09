package com.example.autohub.presentation.model.messenger

import com.example.autohub.presentation.model.user.UserStatus

data class ChatStatus(
    val status: UserStatus? = null,
    val countUnreadMessages: Int = 0
)