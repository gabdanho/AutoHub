package com.example.autohub.presentation.screens.auth.login

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.StringResNamePresentation

data class LoginScreenUiState(
    val isShowPasswordDialog: Boolean = false,
    val isShowSendEmailText: Boolean = false,
    val loadingState: LoadingState? = null,
    val message: StringResNamePresentation? = null,
    val messageDetails: String? = null,

    val emailValue: String = "",
    val passwordValue: String = "",
    val emailForNewPassword: String = "",
)
