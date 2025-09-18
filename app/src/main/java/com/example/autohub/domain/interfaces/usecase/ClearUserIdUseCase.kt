package com.example.autohub.domain.interfaces.usecase

/**
 * UseCase для очистки сохранённого идентификатора пользователя.
 */
interface ClearUserIdUseCase {
    suspend operator fun invoke()
}