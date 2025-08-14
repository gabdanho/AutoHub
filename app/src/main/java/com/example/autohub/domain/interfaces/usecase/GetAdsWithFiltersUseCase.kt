package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.CarAd
import com.example.autohub.domain.model.SearchFilter
import com.example.autohub.domain.model.result.FirebaseResult

interface GetAdsWithFiltersUseCase {
    suspend operator fun invoke(filters: List<SearchFilter>): FirebaseResult<List<CarAd>>
}