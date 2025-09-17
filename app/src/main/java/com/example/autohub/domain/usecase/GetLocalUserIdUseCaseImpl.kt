package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.local.UserPreferencesRepository
import com.example.autohub.domain.interfaces.usecase.GetLocalUserIdUseCase
import javax.inject.Inject

class GetLocalUserIdUseCaseImpl @Inject constructor(
    private val userPreferences: UserPreferencesRepository
) : GetLocalUserIdUseCase {

    override suspend fun invoke(): String? {
        return userPreferences.getUserId()
    }
}