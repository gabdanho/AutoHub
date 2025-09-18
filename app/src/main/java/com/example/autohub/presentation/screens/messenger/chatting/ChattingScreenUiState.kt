package com.example.autohub.presentation.screens.messenger.chatting

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.UiMessage
import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.model.user.UserStatus

/**
 * UI-состояние экрана чата [ChattingScreen].
 *
 * @property messageTextValue Текущий введенный текст сообщения.
 * @property participantStatus Статус участника чата ([UserStatus]).
 * @property authUserData Данные текущего авторизованного пользователя ([User]).
 * @property participantData Данные пользователя-участника ([User]).
 * @property loadingState Состояние загрузки сообщений ([LoadingState]).
 * @property uiMessage Сообщение для отображения пользователю ([UiMessage]).
 * @property isSendButtonEnabled Флаг доступности кнопки отправки сообщения.
 */
data class ChattingScreenUiState(
    val messageTextValue: String = "",
    val participantStatus: UserStatus = UserStatus.Offline,
    val authUserData: User = User(),
    val participantData: User = User(),
    val loadingState: LoadingState? = null,
    val uiMessage: UiMessage = UiMessage(),
    val isSendButtonEnabled: Boolean = true,
)
