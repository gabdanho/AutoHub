package com.example.autohub.domain.interfaces.usecase

/**
 * UseCase для сохранения UID пользователя локально.
 */
interface InsertLocalUserIdUseCase {
    operator fun invoke(uid: String)
}