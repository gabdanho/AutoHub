package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.result.FirebaseResult

/**
 * UseCase для загрузки изображения профиля пользователя в Firebase Storage.
 */
interface UploadUserProfileImageToFirebaseUseCase {
    suspend operator fun invoke(imageRef: ImageUploadData): FirebaseResult<Unit>
}