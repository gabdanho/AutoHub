package com.example.autohub.domain.interfaces.usecase

/**
 * UseCase для получения идентификатора текущего пользователя.
 */
interface GetAuthUserIdUseCase {
    operator fun invoke(): String
}