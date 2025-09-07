package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.local.UserPreferencesRepository
import com.example.autohub.domain.interfaces.usecase.GetLocalUserIdUseCase

class GetLocalUserIdUseCaseImpl(
    private val userPreferences: UserPreferencesRepository
) : GetLocalUserIdUseCase {

    override suspend fun invoke(): String? {
        return userPreferences.getUserId()
    }
}