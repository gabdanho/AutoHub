package com.example.autohub.presentation.navigation.graphs

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.navigation.model.graphs.destinations.AdGraph
import com.example.autohub.presentation.navigation.model.nav_type.CarAdNavType
import com.example.autohub.presentation.navigation.model.nav_type.SearchFilterNavType
import com.example.autohub.presentation.navigation.model.nav_type.SearchFiltersNav
import com.example.autohub.presentation.screens.ad.create.AdCreateScreen
import com.example.autohub.presentation.screens.ad.current.AdScreen
import com.example.autohub.presentation.screens.ad.filters.FiltersScreen
import com.example.autohub.presentation.screens.ad.main.AdsMainScreen
import kotlin.reflect.typeOf

/**
 * Навигационные экраны для графа объявлений.
 *
 * Поддерживает:
 * - Создание объявления ([AdCreateScreen])
 * - Просмотр конкретного объявления ([AdScreen])
 * - Фильтры поиска ([FiltersScreen])
 * - Главный экран с объявлениями ([AdsMainScreen])
 *
 * @param modifier Модификатор Compose для экранов
 */
fun NavGraphBuilder.adNavigationScreensGraph(
    modifier: Modifier = Modifier
) {
    composable<AdGraph.CreateAdScreen> {
        AdCreateScreen(modifier = modifier)
    }

    composable<AdGraph.CurrentAdScreen>(
        typeMap = mapOf(
            typeOf<CarAd>() to CarAdNavType()
        )
    ) {
        val args = it.toRoute<AdGraph.CurrentAdScreen>()
        AdScreen(
            carAd = args.carAd,
            modifier = modifier
        )
    }

    composable<AdGraph.FiltersScreen>(
        typeMap = mapOf(
            typeOf<SearchFiltersNav>() to SearchFilterNavType()
        )
    )  {

        val args = it.toRoute<AdGraph.FiltersScreen>()
        FiltersScreen(
            modifier = modifier,
            searchFilters = args.searchFilters
        )
    }

    composable<AdGraph.AdsMainScreen>(
        typeMap = mapOf(
            typeOf<SearchFiltersNav>() to SearchFilterNavType()
        )
    ) {
        val args = it.toRoute<AdGraph.AdsMainScreen>()
        AdsMainScreen(
            modifier = modifier,
            searchFilters = args.searchFilters
        )
    }
}