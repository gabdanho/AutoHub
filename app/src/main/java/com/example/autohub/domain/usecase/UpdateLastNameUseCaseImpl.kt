package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.UserDataRepository
import com.example.autohub.domain.interfaces.usecase.UpdateLastNameUseCase

class UpdateLastNameUseCaseImpl(
    private val userDataRepository: UserDataRepository
) : UpdateLastNameUseCase {

    override suspend fun invoke(lastName: String) {
        userDataRepository.updateLastName(lastName = lastName)
    }
}