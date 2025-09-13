package com.example.autohub.presentation.navigation.graphs

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.autohub.presentation.navigation.model.graphs.destinations.AuthGraph
import com.example.autohub.presentation.navigation.model.graphs.NavigationGraph
import com.example.autohub.presentation.screens.auth.login.LoginScreen
import com.example.autohub.presentation.screens.auth.register.RegisterScreen

fun NavGraphBuilder.authNavigationScreensGraph(
    modifier: Modifier = Modifier
) {
    navigation<NavigationGraph.Auth>(startDestination = AuthGraph.LoginScreen()) {
        composable<AuthGraph.LoginScreen>{
            val args = it.toRoute<AuthGraph.LoginScreen>()
            LoginScreen(
                isShowSendEmailText = args.isShowSendEmailText,
                modifier = modifier
            )
        }

        composable<AuthGraph.RegistrationScreen> {
            RegisterScreen(modifier = modifier)
        }
    }
}