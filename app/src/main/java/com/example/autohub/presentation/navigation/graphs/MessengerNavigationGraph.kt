package com.example.autohub.presentation.navigation.graphs

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.autohub.presentation.navigation.model.graphs.NavigationGraph
import com.example.autohub.presentation.navigation.model.graphs.destinations.MessengerGraph

fun NavGraphBuilder.messengerNavigationScreensGraph() {
    navigation<NavigationGraph.Messenger>(startDestination = MessengerGraph.MessengerScreen) {
        composable<MessengerGraph.MessengerScreen> {
            Text(text = "MessengerGraph.MessengerScreen")
        }

        composable<MessengerGraph.ChattingScreen> {
            Text(text = "MessengerGraph.ChattingScreen")
        }
    }
}