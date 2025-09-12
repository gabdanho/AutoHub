package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.UserDataRepository
import com.example.autohub.domain.interfaces.usecase.UpdateLastNameUseCase
import com.example.autohub.domain.model.result.FirebaseResult

class UpdateLastNameUseCaseImpl(
    private val userDataRepository: UserDataRepository
) : UpdateLastNameUseCase {

    override suspend fun invoke(lastName: String): FirebaseResult<Unit> {
        return userDataRepository.updateLastName(lastName = lastName)
    }
}