package com.example.autohub.presentation.navigation.model.graphs.destinations

import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.navigation.model.nav_type.UserNav
import kotlinx.serialization.Serializable

@Serializable
sealed interface AdGraph : NavigationDestination {

    @Serializable
    data object CreateAdScreen : AdGraph

    @Serializable
    data class CurrentAdScreen(
        val carAd: CarAd = CarAd(),
        val user: UserNav = UserNav()
    ): AdGraph

    @Serializable
    data object FiltersScreen: AdGraph

    @Serializable
    data object AdsMainScreen: AdGraph
}