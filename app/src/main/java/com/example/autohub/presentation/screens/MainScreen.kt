package com.example.autohub.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.autohub.presentation.navigation.NavigationAction
import com.example.autohub.presentation.navigation.ObserveAsEvents
import com.example.autohub.presentation.navigation.graphs.accountNavigationScreensGraph
import com.example.autohub.presentation.navigation.graphs.adNavigationScreensGraph
import com.example.autohub.presentation.navigation.graphs.authNavigationScreensGraph
import com.example.autohub.presentation.navigation.graphs.messengerNavigationScreensGraph

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel<MainScreenViewModel>()
) {
    val navigator = viewModel.navigator
    val navController = rememberNavController()

    ObserveAsEvents(flow = navigator.navigationActions) { action ->
        when(action) {
            is NavigationAction.Navigate -> navController.navigate(
                action.navigationDestination
            ) {
                action.navOptions(this)
            }
            NavigationAction.NavigateToPopBackStack -> navController.popBackStack()
        }
    }

    NavHost(
        navController = navController,
        startDestination = navigator.startDestination,
        contentAlignment = Alignment.TopCenter
    ) {
        accountNavigationScreensGraph()

        adNavigationScreensGraph()

        authNavigationScreensGraph()

        messengerNavigationScreensGraph()
    }
}