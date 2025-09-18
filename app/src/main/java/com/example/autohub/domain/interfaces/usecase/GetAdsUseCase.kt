package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.ad.CarAd
import com.example.autohub.domain.model.ad.SearchFilter
import com.example.autohub.domain.model.result.FirebaseResult

/**
 * UseCase для получения списка объявлений с возможностью фильтрации и поиска.
 */
interface GetAdsUseCase {
    suspend operator fun invoke(
        searchText: String,
        filters: List<SearchFilter>
    ): FirebaseResult<List<CarAd>>
}