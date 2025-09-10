package com.example.autohub.presentation.navigation.model.nav_type

import com.example.autohub.presentation.model.SearchFilter
import kotlinx.serialization.KSerializer

class SearchFilterNavType(serializer: KSerializer<SearchFilter> = SearchFilter.serializer()) :
    NavTypeSerializer<SearchFilter>(
        serializer = serializer
    )