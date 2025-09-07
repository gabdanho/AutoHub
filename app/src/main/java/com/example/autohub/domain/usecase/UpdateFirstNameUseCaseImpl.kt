package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.UserDataRepository
import com.example.autohub.domain.interfaces.usecase.UpdateFirstNameUseCase

class UpdateFirstNameUseCaseImpl(
    private val userDataRepository: UserDataRepository
) : UpdateFirstNameUseCase {

    override suspend fun invoke(firstName: String) {
        userDataRepository.updateFirstName(firstName = firstName)
    }
}