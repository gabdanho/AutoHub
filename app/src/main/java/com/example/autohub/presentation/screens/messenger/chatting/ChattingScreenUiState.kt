package com.example.autohub.presentation.screens.messenger.chatting

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.model.user.UserStatus

data class ChattingScreenUiState(
    val messageTextValue: String = "",
    val participantStatus: UserStatus = UserStatus.Offline,
    val authUserData: User = User(),
    val participantData: User = User(),
    val loadingState: LoadingState? = null,
    val errorMessage: String = ""
)
