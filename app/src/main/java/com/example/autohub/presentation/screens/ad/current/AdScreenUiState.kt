package com.example.autohub.presentation.screens.ad.current

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.UiMessage
import com.example.autohub.presentation.model.user.User

/**
 * UI-состояние экрана просмотра объявления.
 *
 * @property authUserId ID авторизованного пользователя.
 * @property user Данные пользователя, разместившего объявление.
 * @property loadingState Состояние загрузки данных.
 * @property uiMessage Сообщение для отображения пользователю.
 * @property imageIdToShow Индекс текущего изображения для показа в пейджере.
 * @property isShowImagePager Флаг отображения пейджера изображений.
 */
data class AdScreenUiState(
    val authUserId: String? = null,
    val user: User = User(),
    val loadingState: LoadingState? = null,
    val uiMessage: UiMessage = UiMessage(),
    val imageIdToShow: Int = 0,
    val isShowImagePager: Boolean = false,
)
