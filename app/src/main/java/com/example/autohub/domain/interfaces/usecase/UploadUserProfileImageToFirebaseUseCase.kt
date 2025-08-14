package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.ImageUploadData

interface UploadUserProfileImageToFirebaseUseCase {
    suspend operator fun invoke(imageRef: ImageUploadData)
}