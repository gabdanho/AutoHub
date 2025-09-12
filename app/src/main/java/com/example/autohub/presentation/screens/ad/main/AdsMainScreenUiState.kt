package com.example.autohub.presentation.screens.ad.main

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.ad.CarAd

data class AdsMainScreenUiState(
    val adsList: List<CarAd> = emptyList(),
    val searchTextValue: String = "",
    val loadingState: LoadingState? = null,
)
