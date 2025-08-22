package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.firebase.AdDataRepository
import com.example.autohub.domain.interfaces.usecase.CreateAdUseCase
import com.example.autohub.domain.model.CarAd
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.User
import com.example.autohub.domain.model.result.FirebaseResult

class CreateAdUseCaseImpl(
    private val adDataRepository: AdDataRepository
) : CreateAdUseCase {

    override suspend fun invoke(
        userUID: String,
        carAd: CarAd,
        authUserData: User,
        currentDate: String,
        images: List<ImageUploadData>
    ): FirebaseResult<Unit> {
        return adDataRepository.createAd(
            userUID = userUID,
            carAd = carAd,
            authUserData = authUserData,
            currentDate = currentDate,
            images = images
        )
    }
}