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
        destination: NavigationDestination,
        navOptions: NavOptionsBuilder.() -> Unit
    ) {
        _navigationActions.send(
            NavigationAction.Navigate(
                navigationDestination = destination,
                navOptions = navOptions
            )
        )
    }

    override suspend fun navigatePopBackStack() {
        _navigationActions.send(NavigationAction.NavigateToPopBackStack)
    }
}