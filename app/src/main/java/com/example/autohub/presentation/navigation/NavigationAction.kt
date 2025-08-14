package com.example.autohub.presentation.navigation

import androidx.navigation.NavOptionsBuilder
import com.example.autohub.presentation.navigation.model.graphs.destinations.NavigationDestination

sealed interface NavigationAction {

    data class Navigate(
        val navigationDestination: NavigationDestination,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : NavigationAction

    data object NavigateToPopBackStack : NavigationAction
}