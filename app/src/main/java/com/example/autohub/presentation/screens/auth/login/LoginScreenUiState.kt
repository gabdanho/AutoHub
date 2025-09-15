package com.example.autohub.presentation.screens.auth.login

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.UiMessage

data class LoginScreenUiState(
    val isShowPasswordDialog: Boolean = false,
    val isShowSendEmailText: Boolean = false,
    val loadingState: LoadingState? = null,
    val uiMessage: UiMessage = UiMessage(),

    val emailValue: String = "",
    val passwordValue: String = "",
    val emailForNewPassword: String = "",
)
