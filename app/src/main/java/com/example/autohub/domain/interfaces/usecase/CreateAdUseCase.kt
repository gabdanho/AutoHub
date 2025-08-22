package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.CarAd
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.User
import com.example.autohub.domain.model.result.FirebaseResult

interface CreateAdUseCase {
    suspend operator fun invoke(
        userUID: String,
        carAd: CarAd,
        authUserData: User,
        currentDate: String,
        images: List<ImageUploadData>
    ): FirebaseResult<Unit>
}