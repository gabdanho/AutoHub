package com.example.autohub.presentation.navigation.graphs

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.navigation.model.graphs.destinations.AdGraph
import com.example.autohub.presentation.navigation.model.nav_type.CarAdNavType
import com.example.autohub.presentation.navigation.model.nav_type.UserNav
import com.example.autohub.presentation.navigation.model.nav_type.UserNavType
import com.example.autohub.presentation.screens.ad.current.AdScreen
import kotlin.reflect.typeOf

fun NavGraphBuilder.adNavigationScreensGraph(
    modifier: Modifier = Modifier
) {
    composable<AdGraph.CreateAdScreen> {
        Text(text = "AdGraph.CreateAdScreen")
    }

    composable<AdGraph.CurrentAdScreen>(
        typeMap = mapOf(
            typeOf<UserNav>() to UserNavType(),
            typeOf<CarAd>() to CarAdNavType()
        )
    ) {
        val args = it.toRoute<AdGraph.CurrentAdScreen>()
        AdScreen(
            user = args.user,
            carAd = args.carAd,
            modifier = modifier
        )
    }

    composable<AdGraph.FiltersScreen> {
        Text(text = "AdGraph.FiltersScreen")
    }

    composable<AdGraph.AdsMainScreen> {
        Text(text = "AdGraph.AdsMainScreen")
    }
}