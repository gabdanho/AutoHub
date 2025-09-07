package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.result.FirebaseResult

interface LoginAndSaveUserIdUseCase {
    suspend operator fun invoke(email: String, password: String): FirebaseResult<Unit>
}