package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.firebase.AdDataRepository
import com.example.autohub.domain.interfaces.usecase.UploadAdsImagesToFirebaseUseCase
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.result.FirebaseResult

class UploadAdsImagesToFirebaseUseCaseImpl(
    private val adDataRepository: AdDataRepository
) : UploadAdsImagesToFirebaseUseCase {

    override suspend fun invoke(
        images: List<ImageUploadData>,
        reference: String
    ): FirebaseResult<Unit> {
        return adDataRepository.uploadAdsImagesToFirebase(images = images, reference = reference)
    }
}