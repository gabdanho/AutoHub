package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.UserData
import com.example.autohub.domain.model.result.FirebaseResult

interface RegisterUserUseCase {
    suspend operator fun invoke(
        email: String,
        password: String,
        user: UserData
    ): FirebaseResult<Unit>
}