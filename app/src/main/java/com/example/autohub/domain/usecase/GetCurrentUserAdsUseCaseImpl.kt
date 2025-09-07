package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.AdDataRepository
import com.example.autohub.domain.interfaces.usecase.GetCurrentUserAdsUseCase
import com.example.autohub.domain.model.CarAd
import com.example.autohub.domain.model.result.FirebaseResult

class GetCurrentUserAdsUseCaseImpl(
    private val adDataRepository: AdDataRepository
) : GetCurrentUserAdsUseCase {

    override suspend fun invoke(uid: String): FirebaseResult<List<CarAd>> {
        return adDataRepository.getCurrentUserAds(uid = uid)
    }
}