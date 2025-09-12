package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.result.FirebaseResult

interface UpdateLastNameUseCase {
    suspend operator fun invoke(lastName: String): FirebaseResult<Unit>
}