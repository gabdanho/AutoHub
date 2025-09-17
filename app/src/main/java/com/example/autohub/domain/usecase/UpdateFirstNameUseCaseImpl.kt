package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.UserDataRepository
import com.example.autohub.domain.interfaces.usecase.UpdateFirstNameUseCase
import com.example.autohub.domain.model.result.FirebaseResult
import javax.inject.Inject

class UpdateFirstNameUseCaseImpl @Inject constructor(
    private val userDataRepository: UserDataRepository
) : UpdateFirstNameUseCase {

    override suspend fun invoke(firstName: String): FirebaseResult<Unit> {
        return userDataRepository.updateFirstName(firstName = firstName)
    }
}