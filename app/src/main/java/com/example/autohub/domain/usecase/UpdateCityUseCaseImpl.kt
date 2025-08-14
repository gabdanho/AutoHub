package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.firebase.UserDataRepository
import com.example.autohub.domain.interfaces.usecase.UpdateCityUseCase

class UpdateCityUseCaseImpl(
    private val userDataRepository: UserDataRepository
) : UpdateCityUseCase {

    override suspend fun invoke(city: String) {
        userDataRepository.updateCity(city = city)
    }
}