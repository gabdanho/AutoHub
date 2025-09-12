package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.UserDataRepository
import com.example.autohub.domain.interfaces.usecase.UpdateCityUseCase
import com.example.autohub.domain.model.result.FirebaseResult

class UpdateCityUseCaseImpl(
    private val userDataRepository: UserDataRepository
) : UpdateCityUseCase {

    override suspend fun invoke(city: String): FirebaseResult<Unit> {
        return userDataRepository.updateCity(city = city)
    }
}