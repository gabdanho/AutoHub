package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.User
import com.example.autohub.domain.model.result.FirebaseResult

interface RegisterAndSaveUserIdUseCase {
    suspend operator fun invoke(
        email: String,
        password: String,
        user: User
    ): FirebaseResult<Unit>
}