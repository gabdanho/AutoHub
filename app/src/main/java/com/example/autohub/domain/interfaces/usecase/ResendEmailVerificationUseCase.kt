package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.result.FirebaseResult

interface ResendEmailVerificationUseCase {
    suspend operator fun invoke(email: String, password: String): FirebaseResult<Unit>
}