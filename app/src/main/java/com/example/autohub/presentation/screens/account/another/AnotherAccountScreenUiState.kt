package com.example.autohub.presentation.screens.account.another

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.ad.CarAd

data class AnotherAccountScreenUiState(
    val sellerAds: List<CarAd> = emptyList(),
    val loadingState: LoadingState? = LoadingState.Loading
)
