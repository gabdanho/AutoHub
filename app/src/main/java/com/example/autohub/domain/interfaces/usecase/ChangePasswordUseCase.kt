package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.result.FirebaseResult

/**
 * UseCase для изменения пароля пользователя.
 */
interface ChangePasswordUseCase {
    suspend operator fun invoke(newPassword: String): FirebaseResult<Unit>
}