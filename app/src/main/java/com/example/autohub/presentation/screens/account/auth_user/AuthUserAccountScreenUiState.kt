package com.example.autohub.presentation.screens.account.auth_user

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.UiMessage
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.model.user.User

/**
 * Состояние UI для [AuthUserAccountScreen].
 *
 * @property loadingState Состояние загрузки или ошибки
 * @property uiMessage Сообщение для отображения пользователю
 * @property user Данные авторизованного пользователя
 * @property ads Список объявлений пользователя
 */
data class AuthUserAccountScreenUiState(
    val loadingState: LoadingState? = LoadingState.Loading,
    val uiMessage: UiMessage = UiMessage(),

    val user: User = User(),
    val ads: List<CarAd> = emptyList(),
)