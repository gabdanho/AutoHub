package com.example.autohub.presentation.screens.auth.login

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.UiMessage

/**
 * UI-состояние экрана логина [LoginScreen].
 *
 * @property isShowPasswordDialog Показывать ли диалог восстановления пароля.
 * @property isShowSendEmailText Показывать ли текст повторной отправки письма подтверждения.
 * @property loadingState Состояние загрузки данных ([LoadingState]).
 * @property uiMessage Сообщение для отображения пользователю ([UiMessage]).
 * @property emailValue Текущий введенный email.
 * @property passwordValue Текущий введенный пароль.
 * @property emailForNewPassword Email для восстановления пароля.
 */
data class LoginScreenUiState(
    val isShowPasswordDialog: Boolean = false,
    val isShowSendEmailText: Boolean = false,
    val loadingState: LoadingState? = null,
    val uiMessage: UiMessage = UiMessage(),

    val emailValue: String = "",
    val passwordValue: String = "",
    val emailForNewPassword: String = "",
)
