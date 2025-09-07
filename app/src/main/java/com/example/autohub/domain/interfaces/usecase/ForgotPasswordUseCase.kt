package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.result.FirebaseResult

interface ForgotPasswordUseCase {
    suspend operator fun invoke(email: String): FirebaseResult<Unit>
}