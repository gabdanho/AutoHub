package com.example.autohub.domain.interfaces.usecase

interface InsertLocalUserIdUseCase {
    suspend operator fun invoke(uid: String)
}