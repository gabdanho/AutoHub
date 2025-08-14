package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.firebase.UserDataRepository
import com.example.autohub.domain.interfaces.usecase.UpdateFirstAnsSecondNameUseCase

class UpdateFirstAnsSecondNameUseCaseImpl(
    private val userDataRepository: UserDataRepository
) : UpdateFirstAnsSecondNameUseCase {

    override suspend fun invoke(firstName: String, lastName: String) {
        userDataRepository.updateFirstAnsSecondName(firstName = firstName, lastName = lastName)
    }
}