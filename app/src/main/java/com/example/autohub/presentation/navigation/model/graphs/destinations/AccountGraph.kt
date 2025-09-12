package com.example.autohub.presentation.navigation.model.graphs.destinations

import com.example.autohub.presentation.model.user.User
import kotlinx.serialization.Serializable

@Serializable
sealed interface AccountGraph : NavigationDestination {

    @Serializable
    data class AnotherAccountScreen(
        val user: User = User()
    ) : AccountGraph

    @Serializable
    data object AuthUserAccountScreen: AccountGraph

    @Serializable
    data object AccountSettingsScreen: AccountGraph
}