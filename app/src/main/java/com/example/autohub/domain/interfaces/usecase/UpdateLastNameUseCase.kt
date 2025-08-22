package com.example.autohub.domain.interfaces.usecase

interface UpdateLastNameUseCase {
    suspend operator fun invoke(lastName: String)
}