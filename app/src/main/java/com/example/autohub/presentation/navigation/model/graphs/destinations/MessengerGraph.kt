package com.example.autohub.presentation.navigation.model.graphs.destinations

import com.example.autohub.presentation.model.user.User
import kotlinx.serialization.Serializable

@Serializable
sealed interface MessengerGraph : NavigationDestination {

    @Serializable
    data object MessengerScreen : MessengerGraph

    @Serializable
    data class ChattingScreen(val user: User = User()) : MessengerGraph
}