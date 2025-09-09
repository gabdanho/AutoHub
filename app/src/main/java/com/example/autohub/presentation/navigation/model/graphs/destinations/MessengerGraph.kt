package com.example.autohub.presentation.navigation.model.graphs.destinations

import com.example.autohub.presentation.navigation.model.nav_type.UserNav
import kotlinx.serialization.Serializable

@Serializable
sealed interface MessengerGraph : NavigationDestination {

    @Serializable
    data object MessengerScreen : MessengerGraph

    @Serializable
    data class ChattingScreen(val participant: UserNav = UserNav()) : MessengerGraph
}