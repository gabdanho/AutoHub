package com.example.autohub.presentation.mapper

import com.example.autohub.presentation.model.ad.SearchFilter
import com.example.autohub.domain.model.ad.SearchFilter as SearchFilterDomain

/**
 * Конвертация [SearchFilter] в [SearchFilterDomain].
 *
 * @receiver Presentation-модель фильтра поиска
 * @return Domain-модель фильтра поиска
 */
fun SearchFilter.toSearchFilterDomain(): SearchFilterDomain {
    return SearchFilterDomain(
        name = name,
        value = value
    )
}

/**
 * Конвертация [SearchFilterDomain] в [SearchFilter].
 *
 * @receiver Domain-модель фильтра поиска
 * @return Presentation-модель фильтра поиска
 */
fun SearchFilterDomain.toSearchFilterPresentation(): SearchFilter {
    return SearchFilter(
        name = name,
        value = value
    )
}