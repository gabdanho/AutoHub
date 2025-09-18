package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.result.FirebaseResult

/**
 * UseCase для обновления города пользователя.
 */
interface UpdateCityUseCase {
    suspend operator fun invoke(city: String): FirebaseResult<Unit>
}