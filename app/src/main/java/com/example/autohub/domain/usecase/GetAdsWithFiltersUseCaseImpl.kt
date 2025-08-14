package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.firebase.AdDataRepository
import com.example.autohub.domain.interfaces.usecase.GetAdsWithFiltersUseCase
import com.example.autohub.domain.model.CarAd
import com.example.autohub.domain.model.SearchFilter
import com.example.autohub.domain.model.result.FirebaseResult

class GetAdsWithFiltersUseCaseImpl(
    private val adDataRepository: AdDataRepository
) : GetAdsWithFiltersUseCase {

    override suspend fun invoke(filters: List<SearchFilter>): FirebaseResult<List<CarAd>> {
        return adDataRepository.getAdsWithFilters(filters = filters)
    }
}