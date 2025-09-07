package com.example.autohub.presentation.screens.auth.login

import com.example.autohub.presentation.model.LoadingState

data class LoginScreenUiState(
    val isShowPasswordDialog: Boolean = false,
    val isShowSendEmailText: Boolean = false,
    val loadingState: LoadingState? = null,
    val emailInfoMessage: String? = null,

    val emailValue: String = "",
    val passwordValue: String = "",
    val emailForNewPassword: String = "",
)
