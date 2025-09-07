package com.example.autohub.domain.usecase

import com.example.autohub.data.firebase.model.safeFirebaseCall
import com.example.autohub.domain.interfaces.repository.remote.AuthUserRepository
import com.example.autohub.domain.interfaces.usecase.ForgotPasswordUseCase
import com.example.autohub.domain.model.result.FirebaseResult

class ForgotPasswordUseCaseImpl(
    private val authUserRepository: AuthUserRepository
) : ForgotPasswordUseCase {

    override suspend fun invoke(email: String): FirebaseResult<Unit> {
        return safeFirebaseCall {
            authUserRepository.forgotPassword(email = email)
        }
    }
}