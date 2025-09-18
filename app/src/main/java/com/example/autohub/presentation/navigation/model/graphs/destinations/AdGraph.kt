package com.example.autohub.presentation.navigation.model.graphs.destinations

import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.navigation.model.nav_type.SearchFiltersNav
import kotlinx.serialization.Serializable

/**
 * Граф навигации объявлений.
 *
 * Экранные цели:
 * - [CreateAdScreen] – создание нового объявления
 * - [CurrentAdScreen] – просмотр конкретного объявления ([CarAd])
 * - [FiltersScreen] – фильтры для поиска
 * - [AdsMainScreen] – главный экран с объявлениями и фильтрами
 */
@Serializable
sealed interface AdGraph : NavigationDestination {

    @Serializable
    data object CreateAdScreen : AdGraph

    @Serializable
    data class CurrentAdScreen(val carAd: CarAd): AdGraph

    @Serializable
    data class FiltersScreen(val searchFilters: SearchFiltersNav = SearchFiltersNav()): AdGraph

    @Serializable
    data class AdsMainScreen(val searchFilters: SearchFiltersNav = SearchFiltersNav()): AdGraph
}