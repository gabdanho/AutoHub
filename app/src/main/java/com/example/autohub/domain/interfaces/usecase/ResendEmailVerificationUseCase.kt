package com.example.autohub.domain.interfaces.usecase

interface ResendEmailVerificationUseCase {
    suspend operator fun invoke(email: String, password: String)
}