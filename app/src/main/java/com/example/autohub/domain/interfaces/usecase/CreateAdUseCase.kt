package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.CarAd
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.result.FirebaseResult

interface CreateAdUseCase {
    suspend operator fun invoke(
        carAdInfo: CarAd,
        images: List<ImageUploadData>
    ): FirebaseResult<Unit>
}