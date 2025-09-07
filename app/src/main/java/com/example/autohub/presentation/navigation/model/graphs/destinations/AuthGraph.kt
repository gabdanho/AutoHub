package com.example.autohub.presentation.navigation.model.graphs.destinations

import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthGraph : NavigationDestination {

    @Serializable
    data object LoginScreen : AuthGraph

    @Serializable
    data object RegistrationScreen : AuthGraph
}