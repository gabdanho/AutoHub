package com.example.autohub.presentation.screens.account.another

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.UiMessage
import com.example.autohub.presentation.model.ad.CarAd

/**
 * Состояние UI для [AnotherAccountScreen].
 *
 * @property sellerAds Список объявлений пользователя
 * @property loadingState Состояние загрузки/ошибки
 * @property uiMessage Сообщение для отображения пользователю
 */
data class AnotherAccountScreenUiState(
    val sellerAds: List<CarAd> = emptyList(),
    val loadingState: LoadingState? = LoadingState.Loading,
    val uiMessage: UiMessage = UiMessage(),
)
