package com.example.autohub.presentation.navigation.model.graphs.destinations

import kotlinx.serialization.Serializable

@Serializable
sealed interface AdGraph : NavigationDestination {

    @Serializable
    data object CreateAdScreen : AdGraph

    @Serializable
    data object CurrentAdScreen: AdGraph

    @Serializable
    data object FiltersScreen: AdGraph

    @Serializable
    data object AdsMainScreen: AdGraph
}