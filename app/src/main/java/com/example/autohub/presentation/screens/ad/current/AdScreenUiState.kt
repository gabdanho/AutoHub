package com.example.autohub.presentation.screens.ad.current

import com.example.autohub.presentation.model.LoadingState

data class AdScreenUiState(
    val authUserUid: String = "",
    val loadingState: LoadingState? = null
)
