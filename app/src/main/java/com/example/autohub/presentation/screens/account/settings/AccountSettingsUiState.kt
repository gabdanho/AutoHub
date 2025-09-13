package com.example.autohub.presentation.screens.account.settings

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.user.User

data class AccountSettingsUiState(
    val loadingState: LoadingState? = null,
    val passwordMessage: String? = null,

    val firstNameValue: String = "",
    val lastNameValue: String = "",
    val cityValue: String = "",
    val passwordValue: String = "",

    val user: User = User(),

    val isShowChangePasswordDialog: Boolean = false,
    val isNamesButtonEnabled: Boolean = false,
    val isPasswordError: Boolean = false,
    val isFirstNameValueError: Boolean = false,
    val isLastNameValueError: Boolean = false,
    val isCityValueError: Boolean = false,
)
