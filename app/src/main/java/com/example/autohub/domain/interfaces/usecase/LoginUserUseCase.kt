package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.result.FirebaseResult

interface LoginUserUseCase {
    suspend operator fun invoke(email: String, password: String): FirebaseResult<Unit>
}