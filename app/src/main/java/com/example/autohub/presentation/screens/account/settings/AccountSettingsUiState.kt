package com.example.autohub.presentation.screens.account.settings

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.UiMessage
import com.example.autohub.presentation.model.user.User

/**
 * UI состояние экрана [AccountSettings].
 *
 * @property loadingState текущее состояние загрузки
 * @property uiMessage сообщение для отображения пользователю
 * @property firstNameValue текущее значение имени
 * @property lastNameValue текущее значение фамилии
 * @property cityValue текущее значение города
 * @property passwordValue значение нового пароля
 * @property user данные пользователя
 * @property isShowChangePasswordDialog флаг отображения диалога смены пароля
 * @property isNamesButtonEnabled флаг доступности кнопки изменения имени/фамилии
 * @property isPasswordError флаг ошибки при вводе пароля
 * @property isFirstNameValueError флаг ошибки при вводе имени
 * @property isLastNameValueError флаг ошибки при вводе фамилии
 * @property isCityValueError флаг ошибки при вводе города
 */
data class AccountSettingsUiState(
    val loadingState: LoadingState? = null,
    val uiMessage: UiMessage = UiMessage(),

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
    val isCityValueError: Boolean = true,
)
