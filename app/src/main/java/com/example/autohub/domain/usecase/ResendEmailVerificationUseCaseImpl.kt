package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.AuthUserRepository
import com.example.autohub.domain.interfaces.usecase.ResendEmailVerificationUseCase

class ResendEmailVerificationUseCaseImpl(
    private val authUserRepository: AuthUserRepository
) : ResendEmailVerificationUseCase {

    override suspend fun invoke(email: String, password: String) {
        authUserRepository.resendEmailVerification(email = email, password = password)
    }
}