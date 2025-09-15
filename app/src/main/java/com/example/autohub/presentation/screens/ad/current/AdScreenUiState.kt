package com.example.autohub.presentation.screens.ad.current

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.UiMessage
import com.example.autohub.presentation.model.user.User

data class AdScreenUiState(
    val authUserId: String = "",
    val user: User = User(),
    val loadingState: LoadingState? = null,
    val uiMessage: UiMessage = UiMessage(),
    val imageToShow: String? = null,
)
