package com.example.autohub.presentation.screens.account.auth_user

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.model.user.User

data class AuthUserAccountScreenUiState(
    val loadingState: LoadingState? = LoadingState.Loading,

    val user: User = User(),
    val ads: List<CarAd> = emptyList(),
)