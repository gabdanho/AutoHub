package com.example.autohub.domain.interfaces.usecase

interface UpdateFirstNameUseCase {
    suspend operator fun invoke(firstName: String)
}