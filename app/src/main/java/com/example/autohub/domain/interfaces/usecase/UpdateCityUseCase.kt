package com.example.autohub.domain.interfaces.usecase

interface UpdateCityUseCase {
    suspend operator fun invoke(city: String)
}