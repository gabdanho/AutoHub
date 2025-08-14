package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.firebase.AdDataRepository
import com.example.autohub.domain.interfaces.usecase.GetAdsBySearchTextAndFiltersUseCase
import com.example.autohub.domain.model.CarAd
import com.example.autohub.domain.model.SearchFilter
import com.example.autohub.domain.model.result.FirebaseResult

class GetAdsBySearchTextAndFiltersUseCaseImpl(
    private val adDataRepository: AdDataRepository
) : GetAdsBySearchTextAndFiltersUseCase {

    override suspend fun invoke(
        searchText: String,
        filters: List<SearchFilter>
    ): FirebaseResult<List<CarAd>> {
        return adDataRepository.getAdsBySearchTextAndFilters(
            searchText = searchText,
            filters = filters
        )
    }
}