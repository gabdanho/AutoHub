package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.result.FirebaseResult

interface UpdateCityUseCase {
    suspend operator fun invoke(city: String): FirebaseResult<Unit>
}