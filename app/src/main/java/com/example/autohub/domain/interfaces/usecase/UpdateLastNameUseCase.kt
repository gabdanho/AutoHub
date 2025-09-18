package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.result.FirebaseResult

/**
 * UseCase для обновления фамилии пользователя.
 */
interface UpdateLastNameUseCase {
    suspend operator fun invoke(lastName: String): FirebaseResult<Unit>
}