package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.AuthUserRepository
import com.example.autohub.domain.interfaces.usecase.ForgotPasswordUseCase
import com.example.autohub.domain.model.result.FirebaseResult
import javax.inject.Inject

class ForgotPasswordUseCaseImpl @Inject constructor(
    private val authUserRepository: AuthUserRepository
) : ForgotPasswordUseCase {

    override suspend fun invoke(email: String): FirebaseResult<Unit> {
        return authUserRepository.forgotPassword(email = email)
    }
}