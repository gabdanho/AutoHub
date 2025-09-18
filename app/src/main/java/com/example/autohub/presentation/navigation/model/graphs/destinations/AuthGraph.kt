package com.example.autohub.presentation.navigation.model.graphs.destinations

import kotlinx.serialization.Serializable

/**
 * Граф навигации аутентификации.
 *
 * Экранные цели:
 * - [LoginScreen] – экран логина, с опциональным отображением текста об отправке email
 * - [RegistrationScreen] – экран регистрации
 */
@Serializable
sealed interface AuthGraph : NavigationDestination {

    @Serializable
    data class LoginScreen(val isShowSendEmailText: Boolean = false) : AuthGraph

    @Serializable
    data object RegistrationScreen : AuthGraph
}