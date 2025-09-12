package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.UserDataRepository
import com.example.autohub.domain.interfaces.usecase.UploadUserProfileImageToFirebaseUseCase
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.result.FirebaseResult

class UploadUserProfileImageToFirebaseUseCaseImpl(
    private val userDataRepository: UserDataRepository
) : UploadUserProfileImageToFirebaseUseCase {

    override suspend fun invoke(imageRef: ImageUploadData): FirebaseResult<Unit> {
        return userDataRepository.uploadUserProfileImageToFirebase(imageRef = imageRef)
    }
}