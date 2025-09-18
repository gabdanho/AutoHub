package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.result.FirebaseResult

/**
 * UseCase для загрузки изображений объявлений в Firebase Storage.
 */
interface UploadAdsImagesToFirebaseUseCase {
    suspend operator fun invoke(
        images: List<ImageUploadData>,
        reference: String
    ): FirebaseResult<Unit>
}