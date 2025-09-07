package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.AdDataRepository
import com.example.autohub.domain.interfaces.usecase.CreateAdUseCase
import com.example.autohub.domain.model.CarAd
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.result.FirebaseResult

class CreateAdUseCaseImpl(
    private val adDataRepository: AdDataRepository
) : CreateAdUseCase {

    override suspend fun invoke(
        carAdInfo: CarAd,
        images: List<ImageUploadData>
    ): FirebaseResult<Unit> {
        return adDataRepository.createAd(
            carAdInfo = carAdInfo,
            images = images
        )
    }
}