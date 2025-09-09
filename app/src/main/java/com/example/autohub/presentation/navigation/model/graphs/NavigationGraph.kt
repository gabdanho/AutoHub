package com.example.autohub.presentation.navigation.model.graphs

import com.example.autohub.presentation.navigation.model.graphs.destinations.NavigationDestination
import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationGraph : NavigationDestination {

    @Serializable
    data object Auth : NavigationGraph

    @Serializable
    data object Messenger : NavigationGraph
}