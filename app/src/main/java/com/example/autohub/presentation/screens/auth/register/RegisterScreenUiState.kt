package com.example.autohub.presentation.screens.auth.register

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.UiMessage

data class RegisterScreenUiState(
    val firstNameValue: String = "",
    val lastNameValue: String = "",
    val emailValue: String = "",
    val phoneValue: String = "",
    val cityValue: String = "",
    val passwordValue: String = "",
    val repeatPasswordValue: String = "",

    val isFirstNameError: Boolean = false,
    val isLastNameError: Boolean = false,
    val isEmailError: Boolean = false,
    val isPhoneError: Boolean = false,
    val isCityError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isRepeatPasswordError: Boolean = false,

    val loadingState: LoadingState? = null,
    val uiMessage: UiMessage = UiMessage(),
)
