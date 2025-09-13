package com.example.autohub.presentation.screens.messenger.main

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.StringResNamePresentation

data class MessengerScreenUiState(
    val message: StringResNamePresentation? = null,
    val loadingState: LoadingState? = null,
)
