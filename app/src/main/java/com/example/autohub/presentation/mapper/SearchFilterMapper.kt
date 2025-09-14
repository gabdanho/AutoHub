package com.example.autohub.presentation.mapper

import com.example.autohub.presentation.model.SearchFilter
import com.example.autohub.domain.model.ad.SearchFilter as SearchFilterDomain

fun SearchFilter.toSearchFilterDomain(): SearchFilterDomain {
    return SearchFilterDomain(
        name = name,
        value = value
    )
}

fun SearchFilterDomain.toSearchFilterPresentation(): SearchFilter {
    return SearchFilter(
        name = name,
        value = value
    )
}