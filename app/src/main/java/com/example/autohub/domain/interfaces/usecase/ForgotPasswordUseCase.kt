package com.example.autohub.domain.interfaces.usecase

interface ForgotPasswordUseCase {
    suspend operator fun invoke(email: String)
}