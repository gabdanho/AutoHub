package com.example.autohub.presentation.screens.ad.main

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.UiMessage
import com.example.autohub.presentation.model.ad.CarAd

/**
 * UI-состояние экрана [AdsMainScreen].
 *
 * @property adsList Список объявлений для отображения.
 * @property searchTextValue Текущий текст поиска.
 * @property loadingState Состояние загрузки данных ([LoadingState]).
 * @property uiMessage Сообщение для отображения пользователю ([UiMessage]).
 */
data class AdsMainScreenUiState(
    val adsList: List<CarAd> = emptyList(),
    val searchTextValue: String = "",
    val loadingState: LoadingState? = null,
    val uiMessage: UiMessage = UiMessage(),
)
