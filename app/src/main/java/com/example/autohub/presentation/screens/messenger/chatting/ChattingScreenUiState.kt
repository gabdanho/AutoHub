package com.example.autohub.presentation.screens.messenger.chatting

import com.example.autohub.presentation.model.user.UserStatus

data class ChattingScreenUiState(
    val messageTextValue: String = "",
    val participantStatus: UserStatus = UserStatus.Offline,
    val participantId: String = "",
    val authUserId: String = "",
    val errorMessage: String = ""
)
