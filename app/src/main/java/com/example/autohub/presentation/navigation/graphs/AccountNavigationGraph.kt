package com.example.autohub.presentation.navigation.graphs

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.navigation.model.graphs.destinations.AccountGraph
import com.example.autohub.presentation.navigation.model.nav_type.ListCarAdNav
import com.example.autohub.presentation.navigation.model.nav_type.ListCarAdNavType
import com.example.autohub.presentation.navigation.model.nav_type.UserNavType
import com.example.autohub.presentation.screens.account.another.AnotherAccountScreen
import kotlin.reflect.typeOf

fun NavGraphBuilder.accountNavigationScreensGraph(
    modifier: Modifier = Modifier
) {
    composable<AccountGraph.AnotherAccountScreen>(
        typeMap = mapOf(
            typeOf<User>() to UserNavType(),
            typeOf<ListCarAdNav>() to ListCarAdNavType()
        )
    ) {
        val args = it.toRoute<AccountGraph.AnotherAccountScreen>()
        AnotherAccountScreen(
            user = args.user,
            modifier = modifier
        )
    }

    composable<AccountGraph.AccountSettingsScreen> {
        Text(text = "AccountGraph.AccountSettingsScreen")
    }

    composable<AccountGraph.AuthUserAccountScreen> {
        Text(text = "AccountGraph.AuthUserAccountScreen")
    }
}