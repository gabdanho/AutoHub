package com.example.autohub.presentation.navigation.model.graphs

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationGraph {

    @Serializable
    data object Auth : NavigationGraph

    @Serializable
    data object Messenger : NavigationGraph
}