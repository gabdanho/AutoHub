package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.user.UserStatus

/**
 * UseCase для изменения статуса пользователя.
 */
interface ChangeUserStatusUseCase {
    suspend operator fun invoke(status: UserStatus)
}