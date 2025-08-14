package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.firebase.UserDataRepository
import com.example.autohub.domain.interfaces.usecase.UploadUserProfileImageToFirebaseUseCase
import com.example.autohub.domain.model.ImageUploadData

class UploadUserProfileImageToFirebaseUseCaseImpl(
    private val userDataRepository: UserDataRepository
) : UploadUserProfileImageToFirebaseUseCase {

    override suspend fun invoke(imageRef: ImageUploadData) {
        userDataRepository.uploadUserProfileImageToFirebase(imageRef = imageRef)
    }
}