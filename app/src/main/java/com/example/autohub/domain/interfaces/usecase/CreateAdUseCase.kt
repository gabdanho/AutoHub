package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.ad.CarAd
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.result.FirebaseResult

/**
 * UseCase для создания нового объявления о продаже автомобиля.
 */
interface CreateAdUseCase {
    suspend operator fun invoke(
        carAdInfo: CarAd,
        images: List<ImageUploadData>
    ): FirebaseResult<Unit>
}