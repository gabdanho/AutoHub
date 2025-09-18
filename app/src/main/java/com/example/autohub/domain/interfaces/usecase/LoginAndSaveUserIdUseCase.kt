package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.result.FirebaseResult

/**
 * UseCase для входа пользователя и сохранения его UID локально.
 */
interface LoginAndSaveUserIdUseCase {
    suspend operator fun invoke(email: String, password: String): FirebaseResult<Unit>
}