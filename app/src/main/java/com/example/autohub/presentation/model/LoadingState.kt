package com.example.autohub.presentation.model

/**
 * Состояние загрузки для UI.
 */
sealed class LoadingState {

    /** Процесс загрузки данных */
    data object Loading : LoadingState()

    /** Данные успешно загружены */
    data object Success : LoadingState()

    /** Ошибка при загрузке */
    data class Error(val message: String?) : LoadingState()
}