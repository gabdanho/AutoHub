package com.example.autohub.presentation.navigation.graphs

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.navigation.model.graphs.NavigationGraph
import com.example.autohub.presentation.navigation.model.graphs.destinations.MessengerGraph
import com.example.autohub.presentation.navigation.model.nav_type.UserNavType
import com.example.autohub.presentation.screens.messenger.chatting.ChattingScreen
import com.example.autohub.presentation.screens.messenger.main.MessengerScreen
import kotlin.reflect.typeOf

fun NavGraphBuilder.messengerNavigationScreensGraph(
    modifier: Modifier = Modifier
) {
    navigation<NavigationGraph.Messenger>(startDestination = MessengerGraph.MessengerScreen) {
        composable<MessengerGraph.MessengerScreen> {
            MessengerScreen(modifier = modifier)
        }

        composable<MessengerGraph.ChattingScreen>(
            typeMap = mapOf(
                typeOf<User>() to UserNavType()
            )
        ) {
            val args = it.toRoute<MessengerGraph.ChattingScreen>()
            ChattingScreen(participant = args.participant)
        }
    }
}