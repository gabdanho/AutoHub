package com.example.autohub.presentation.navigation.model.graphs.destinations

import com.example.autohub.presentation.model.user.User
import kotlinx.serialization.Serializable

/**
 * Граф навигации мессенджера.
 *
 * Экранные цели:
 * - [MessengerScreen] – главный экран мессенджера
 * - [ChattingScreen] – экран чата с конкретным участником ([User])
 */
@Serializable
sealed interface MessengerGraph : NavigationDestination {

    @Serializable
    data object MessengerScreen : MessengerGraph

    @Serializable
    data class ChattingScreen(val participant: User = User()) : MessengerGraph
}