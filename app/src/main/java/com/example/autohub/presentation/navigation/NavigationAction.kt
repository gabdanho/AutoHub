package com.example.autohub.presentation.navigation

import androidx.navigation.NavOptionsBuilder
import com.example.autohub.presentation.navigation.model.graphs.destinations.NavigationDestination

/**
 * События навигации, которые может обрабатывать [Navigator].
 */
sealed interface NavigationAction {

    /**
     * Навигация к экрану [navigationDestination] с опциональными [navOptions].
     */
    data class Navigate(
        val navigationDestination: NavigationDestination,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : NavigationAction

    /**
     * Навигация назад в стеке (popBackStack).
     */
    data object NavigateToPopBackStack : NavigationAction
}