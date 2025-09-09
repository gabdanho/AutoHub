package com.example.autohub.presentation.navigation.graphs

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.autohub.presentation.navigation.model.graphs.destinations.AccountGraph
import com.example.autohub.presentation.navigation.model.nav_type.UserNav
import com.example.autohub.presentation.navigation.model.nav_type.UserNavType
import com.example.autohub.presentation.screens.account.another.AnotherAccountScreen
import com.example.autohub.presentation.screens.account.auth_user.AuthUserAccountScreen
import com.example.autohub.presentation.screens.account.settings.AccountSettings
import kotlin.reflect.typeOf

fun NavGraphBuilder.accountNavigationScreensGraph(
    modifier: Modifier = Modifier
) {
    composable<AccountGraph.AnotherAccountScreen>(
        typeMap = mapOf(
            typeOf<UserNav>() to UserNavType()
        )
    ) {
        val args = it.toRoute<AccountGraph.AnotherAccountScreen>()
        AnotherAccountScreen(
            user = args.user,
            modifier = modifier
        )
    }

    composable<AccountGraph.AccountSettingsScreen> {
        AccountSettings(modifier = modifier)
    }

    composable<AccountGraph.AuthUserAccountScreen> {
        AuthUserAccountScreen(modifier = modifier)
    }
}