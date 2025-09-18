package com.example.autohub.domain.interfaces.usecase

/**
 * UseCase для выхода пользователя и очистки локального UID.
 */
interface SignOutAndClearUserIdUseCase {
    suspend operator fun invoke()
}