package com.example.autohub.presentation.screens.auth.register

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.UiMessage

/**
 * UI-состояние экрана регистрации [RegisterScreen].
 *
 * @property firstNameValue Введенное имя.
 * @property lastNameValue Введенная фамилия.
 * @property emailValue Введенный email.
 * @property phoneValue Введенный телефон.
 * @property cityValue Введенный город.
 * @property passwordValue Введенный пароль.
 * @property repeatPasswordValue Повторно введенный пароль.
 * @property isFirstNameError Флаг ошибки имени.
 * @property isLastNameError Флаг ошибки фамилии.
 * @property isEmailError Флаг ошибки email.
 * @property isPhoneError Флаг ошибки телефона.
 * @property isCityError Флаг ошибки города.
 * @property isPasswordError Флаг ошибки пароля.
 * @property isRepeatPasswordError Флаг ошибки повторного пароля.
 * @property loadingState Состояние загрузки ([LoadingState]).
 * @property uiMessage Сообщение для отображения пользователю ([UiMessage]).
 */
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
