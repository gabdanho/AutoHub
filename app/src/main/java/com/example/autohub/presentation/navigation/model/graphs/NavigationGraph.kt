package com.example.autohub.presentation.navigation.model.graphs

import com.example.autohub.presentation.navigation.model.graphs.destinations.NavigationDestination
import kotlinx.serialization.Serializable

/**
 * Сущность, описывающая основные графы навигации приложения.
 *
 * Используется для структурирования экранов по логическим блокам (графам):
 * - Auth — граф экранов авторизации
 * - Messenger — граф экранов мессенджера
 *
 * Наследуется от [NavigationDestination], поэтому может использоваться в навигационных событиях.
 */
@Serializable
sealed interface NavigationGraph : NavigationDestination {

    @Serializable
    data object Auth : NavigationGraph

    @Serializable
    data object Messenger : NavigationGraph
}