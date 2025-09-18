package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.ad.CarAd
import com.example.autohub.domain.model.result.FirebaseResult

/**
 * UseCase для получения объявлений, созданных текущим пользователем.
 */
interface GetCurrentUserAdsUseCase {
    suspend operator fun invoke(uid: String): FirebaseResult<List<CarAd>>
}