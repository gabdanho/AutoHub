package com.example.autohub.domain.interfaces.usecase

interface UpdateFirstAnsSecondNameUseCase {
    suspend operator fun invoke(
        firstName: String,
        lastName: String
    )
}