package com.example.autohub.presentation.navigation.graphs

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.autohub.presentation.navigation.model.graphs.destinations.AdGraph

fun NavGraphBuilder.adNavigationScreensGraph(
    modifier: Modifier = Modifier
) {
    composable<AdGraph.CreateAdScreen> {
        Text(text = "AdGraph.CreateAdScreen")
    }

    composable<AdGraph.CurrentAdScreen> {
        Text(text = "AdGraph.CurrentAdScreen")
    }

    composable<AdGraph.FiltersScreen> {
        Text(text = "AdGraph.FiltersScreen")
    }

    composable<AdGraph.AdsMainScreen> {
        Text(text = "AdGraph.AdsMainScreen")
    }
}