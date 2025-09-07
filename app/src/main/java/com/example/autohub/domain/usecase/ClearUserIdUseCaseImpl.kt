package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.local.UserPreferencesRepository
import com.example.autohub.domain.interfaces.usecase.ClearUserIdUseCase

class ClearUserIdUseCaseImpl(
    private val userPreferences: UserPreferencesRepository
) : ClearUserIdUseCase {

    override suspend fun invoke() {
        userPreferences.clearUserId()
    }
}