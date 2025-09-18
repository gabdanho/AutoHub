package com.example.autohub.domain.interfaces.usecase

/**
 * UseCase для выхода пользователя.
 */
interface SignOutUseCase {
    suspend operator fun invoke()
}