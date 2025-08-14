package com.example.autohub.presentation.navigation.graphs

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.autohub.presentation.navigation.model.graphs.destinations.AuthGraph
import com.example.autohub.presentation.navigation.model.graphs.NavigationGraph

fun NavGraphBuilder.authNavigationScreensGraph(
    modifier: Modifier = Modifier
) {
    navigation<NavigationGraph.Auth>(startDestination = AuthGraph.LoginScreen) {
        composable<AuthGraph.LoginScreen> {
            Text(text = "AuthGraph.LoginScreen")
        }

        composable<AuthGraph.RegistrationScreen> {
            Text(text = "AuthGraph.RegistrationScreen")
        }
    }
}