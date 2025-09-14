package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.user.User
import com.example.autohub.domain.model.result.FirebaseResult

interface RegisterUserUseCase {
    suspend operator fun invoke(
        email: String,
        password: String,
        user: User
    ): FirebaseResult<Unit>
}