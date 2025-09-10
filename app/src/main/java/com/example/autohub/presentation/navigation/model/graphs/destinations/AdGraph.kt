package com.example.autohub.presentation.navigation.model.graphs.destinations

import com.example.autohub.presentation.model.SearchFilter
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.navigation.model.nav_type.UserNav
import kotlinx.serialization.Serializable

@Serializable
sealed interface AdGraph : NavigationDestination {

    @Serializable
    data object CreateAdScreen : AdGraph

    @Serializable
    data class CurrentAdScreen(val carAd: CarAd = CarAd()): AdGraph

    @Serializable
    data object FiltersScreen: AdGraph

    @Serializable
    data class AdsMainScreen(val filters: List<SearchFilter> = emptyList()): AdGraph
}