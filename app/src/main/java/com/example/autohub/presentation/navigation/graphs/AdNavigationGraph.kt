package com.example.autohub.presentation.navigation.graphs

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.autohub.presentation.model.SearchFilter
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.navigation.model.graphs.destinations.AdGraph
import com.example.autohub.presentation.navigation.model.nav_type.CarAdNavType
import com.example.autohub.presentation.navigation.model.nav_type.SearchFilterNavType
import com.example.autohub.presentation.navigation.model.nav_type.UserNav
import com.example.autohub.presentation.navigation.model.nav_type.UserNavType
import com.example.autohub.presentation.screens.ad.create.AdCreateScreen
import com.example.autohub.presentation.screens.ad.current.AdScreen
import com.example.autohub.presentation.screens.ad.filters.FiltersScreen
import com.example.autohub.presentation.screens.ad.main.AdsMainScreen
import kotlin.reflect.typeOf

fun NavGraphBuilder.adNavigationScreensGraph(
    modifier: Modifier = Modifier
) {
    composable<AdGraph.CreateAdScreen> {
        AdCreateScreen(modifier = modifier)
    }

    composable<AdGraph.CurrentAdScreen>(
        typeMap = mapOf(
            typeOf<UserNav>() to UserNavType(),
            typeOf<CarAd>() to CarAdNavType()
        )
    ) {
        val args = it.toRoute<AdGraph.CurrentAdScreen>()
        AdScreen(
            carAd = args.carAd,
            modifier = modifier
        )
    }

    composable<AdGraph.FiltersScreen> {
        FiltersScreen(modifier = modifier)
    }

    composable<AdGraph.AdsMainScreen>(
        typeMap = mapOf(
            typeOf<SearchFilter>() to SearchFilterNavType()
        )
    ) {
        val args = it.toRoute<AdGraph.AdsMainScreen>()
        AdsMainScreen(
            modifier = modifier,
            filters = args.filters
        )
    }
}