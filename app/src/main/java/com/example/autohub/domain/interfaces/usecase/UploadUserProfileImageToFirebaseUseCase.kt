package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.result.FirebaseResult

interface UploadUserProfileImageToFirebaseUseCase {
    suspend operator fun invoke(imageRef: ImageUploadData): FirebaseResult<Unit>
}