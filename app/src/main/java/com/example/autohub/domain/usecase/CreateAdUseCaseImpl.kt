package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.AdDataRepository
import com.example.autohub.domain.interfaces.usecase.CreateAdUseCase
import com.example.autohub.domain.interfaces.usecase.HasInternetConnectionUseCase
import com.example.autohub.domain.model.ad.CarAd
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.domain.model.result.HandleErrorTag
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class CreateAdUseCaseImpl @Inject constructor(
    private val adDataRepository: AdDataRepository,
    private val hasInternetConnectionUseCase: HasInternetConnectionUseCase,
) : CreateAdUseCase {

    override suspend fun invoke(
        carAdInfo: CarAd,
        images: List<ImageUploadData>
    ): FirebaseResult<Unit> {
        val isOnline = hasInternetConnectionUseCase().firstOrNull() ?: false

        if (!isOnline) return FirebaseResult.Error.HandledError(tag = HandleErrorTag.NO_INTERNET)

        return adDataRepository.createAd(
            carAdInfo = carAdInfo,
            images = images
        )
    }
}