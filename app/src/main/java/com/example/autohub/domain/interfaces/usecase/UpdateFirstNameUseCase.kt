package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.result.FirebaseResult

/**
 * UseCase для обновления имени пользователя.
 */
interface UpdateFirstNameUseCase {
    suspend operator fun invoke(firstName: String): FirebaseResult<Unit>
}