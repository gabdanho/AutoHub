package com.example.autohub.presentation.navigation.model.graphs.destinations

import com.example.autohub.presentation.navigation.model.nav_type.UserNav
import kotlinx.serialization.Serializable

@Serializable
sealed interface AccountGraph : NavigationDestination {

    @Serializable
    data class AnotherAccountScreen(
        val user: UserNav = UserNav()
    ) : AccountGraph

    @Serializable
    data object AuthUserAccountScreen: AccountGraph

    @Serializable
    data object AccountSettingsScreen: AccountGraph
}