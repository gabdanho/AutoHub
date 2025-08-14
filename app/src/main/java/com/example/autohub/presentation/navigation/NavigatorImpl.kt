package com.example.autohub.presentation.navigation

import androidx.navigation.NavOptionsBuilder
import com.example.autohub.presentation.navigation.model.graphs.destinations.NavigationDestination
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class NavigatorImpl(
    override val startDestination: NavigationDestination
) : Navigator {

    private val _navigationActions = Channel<NavigationAction>()
    override val navigationActions = _navigationActions.receiveAsFlow()

    override suspend fun navigate(
        navigationDestination: NavigationDestination,
        navOptions: NavOptionsBuilder.() -> Unit
    ) {
        _navigationActions.send(
            NavigationAction.Navigate(
                navigationDestination = navigationDestination,
                navOptions = navOptions
            )
        )
    }

    override suspend fun navigatePopBackStack() {
        _navigationActions.send(NavigationAction.NavigateToPopBackStack)
    }
}