package com.example.autohub.presentation.screens.messenger.main

import com.example.autohub.presentation.model.LoadingState

data class MessengerScreenUiState(
    val message: String? = null,
    val loadingState: LoadingState? = null,
)
