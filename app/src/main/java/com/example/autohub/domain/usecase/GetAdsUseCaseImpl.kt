package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.firebase.AdDataRepository
import com.example.autohub.domain.interfaces.usecase.GetAdsUseCase
import com.example.autohub.domain.model.CarAd
import com.example.autohub.domain.model.SearchFilter
import com.example.autohub.domain.model.result.FirebaseResult

class GetAdsUseCaseImpl(
    private val adDataRepository: AdDataRepository
) : GetAdsUseCase {

    override suspend fun invoke(
        searchText: String,
        filters: List<SearchFilter>
    ): FirebaseResult<List<CarAd>> {
        return adDataRepository.getAds(
            searchText = searchText,
            filters = filters
        )
    }
}