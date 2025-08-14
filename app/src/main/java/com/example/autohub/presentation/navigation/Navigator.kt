package com.example.autohub.presentation.navigation

import androidx.navigation.NavOptionsBuilder
import com.example.autohub.presentation.navigation.model.graphs.destinations.NavigationDestination
import kotlinx.coroutines.flow.Flow

interface Navigator {

    val startDestination: NavigationDestination

    val navigationActions: Flow<NavigationAction>

    suspend fun navigate(
        destination: NavigationDestination,
        navOptions: NavOptionsBuilder.() -> Unit = {}
    )

    suspend fun navigatePopBackStack()
}