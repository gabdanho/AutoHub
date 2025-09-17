package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.AdDataRepository
import com.example.autohub.domain.interfaces.usecase.GetAdsUseCase
import com.example.autohub.domain.model.ad.CarAd
import com.example.autohub.domain.model.ad.SearchFilter
import com.example.autohub.domain.model.result.FirebaseResult
import javax.inject.Inject

class GetAdsUseCaseImpl @Inject constructor(
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