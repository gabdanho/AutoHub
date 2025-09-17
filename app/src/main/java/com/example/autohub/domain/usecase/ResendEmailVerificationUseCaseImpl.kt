package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.AuthUserRepository
import com.example.autohub.domain.interfaces.usecase.ResendEmailVerificationUseCase
import com.example.autohub.domain.model.result.FirebaseResult
import javax.inject.Inject

class ResendEmailVerificationUseCaseImpl @Inject constructor(
    private val authUserRepository: AuthUserRepository
) : ResendEmailVerificationUseCase {

    override suspend fun invoke(email: String, password: String): FirebaseResult<Unit> {
        return authUserRepository.resendEmailVerification(email = email, password = password)
    }
}