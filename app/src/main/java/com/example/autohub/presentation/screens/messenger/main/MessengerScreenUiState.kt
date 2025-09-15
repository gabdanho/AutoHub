package com.example.autohub.presentation.screens.messenger.main

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.UiMessage

data class MessengerScreenUiState(
    val uiMessage: UiMessage = UiMessage(),
    val loadingState: LoadingState? = null,
)
