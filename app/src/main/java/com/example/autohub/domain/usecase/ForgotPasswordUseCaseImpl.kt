package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.AuthUserRepository
import com.example.autohub.domain.interfaces.usecase.ForgotPasswordUseCase

class ForgotPasswordUseCaseImpl(
    private val authUserRepository: AuthUserRepository
) : ForgotPasswordUseCase {

    override suspend fun invoke(email: String) {
        authUserRepository.forgotPassword(email = email)
    }
}