package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.AuthUserRepository
import com.example.autohub.domain.interfaces.usecase.ChangePasswordUseCase
import com.example.autohub.domain.model.result.FirebaseResult

class ChangePasswordUseCaseImpl(
    private val authUserRepository: AuthUserRepository
) : ChangePasswordUseCase {

    override suspend fun invoke(newPassword: String): FirebaseResult<Unit> {
        return authUserRepository.changePassword(newPassword = newPassword)
    }
}