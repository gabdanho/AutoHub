package com.example.autohub.presentation.screens.messenger.main

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.UiMessage

/**
 * Состояние UI экрана MessengerScreen.
 *
 * @property uiMessage Сообщение, отображаемое пользователю.
 * @property loadingState Текущее состояние загрузки экрана.
 */
data class MessengerScreenUiState(
    val uiMessage: UiMessage = UiMessage(),
    val loadingState: LoadingState? = null,
)
