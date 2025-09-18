package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.result.FirebaseResult

/**
 * UseCase для повторной отправки письма подтверждения email.
 */
interface ResendEmailVerificationUseCase {
    suspend operator fun invoke(email: String, password: String): FirebaseResult<Unit>
}