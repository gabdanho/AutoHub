package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.user.User
import com.example.autohub.domain.model.result.FirebaseResult

/**
 * UseCase для получения информации о пользователе по UID.
 */
interface GetUserDataUseCase {
    suspend operator fun invoke(userId: String): FirebaseResult<User>
}