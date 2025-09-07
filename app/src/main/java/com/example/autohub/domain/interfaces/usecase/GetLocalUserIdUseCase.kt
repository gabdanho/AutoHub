package com.example.autohub.domain.interfaces.usecase

interface GetLocalUserIdUseCase {
    suspend operator fun invoke(): String?
}