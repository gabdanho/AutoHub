package com.example.autohub.presentation.navigation.model.nav_type

import androidx.compose.runtime.Immutable
import com.example.autohub.presentation.model.SearchFilter
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class SearchFiltersNav(
    val filters: List<SearchFilter> = emptyList()
)

class SearchFilterNavType(serializer: KSerializer<SearchFiltersNav> = SearchFiltersNav.serializer()) :
    NavTypeSerializer<SearchFiltersNav>(
        serializer = serializer
    )