package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.CarAd
import com.example.autohub.domain.model.result.FirebaseResult

interface GetCurrentUserAdsUseCase {
    suspend operator fun invoke(uid: String): FirebaseResult<List<CarAd>>
}