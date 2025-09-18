package com.example.autohub.domain.interfaces.usecase

/**
 * UseCase для получения локально сохранённого UID пользователя.
 */
interface GetLocalUserIdUseCase {
    suspend operator fun invoke(): String?
}