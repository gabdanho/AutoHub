package com.example.autohub.domain.interfaces.usecase

interface InsertTokenUseCase {
    suspend operator fun invoke(token: String)
}