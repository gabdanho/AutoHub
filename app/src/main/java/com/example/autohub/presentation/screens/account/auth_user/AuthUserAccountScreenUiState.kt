package com.example.autohub.presentation.screens.account.auth_user

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.StringResNamePresentation
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.model.user.User

data class AuthUserAccountScreenUiState(
    val loadingState: LoadingState? = LoadingState.Loading,
    val message: StringResNamePresentation? = null,

    val user: User = User(),
    val ads: List<CarAd> = emptyList(),
)