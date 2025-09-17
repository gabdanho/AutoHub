package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.local.UserPreferencesRepository
import com.example.autohub.domain.interfaces.usecase.InsertLocalUserIdUseCase
import javax.inject.Inject

class InsertLocalUserIdUseCaseImpl @Inject constructor(
    private val userPreferences: UserPreferencesRepository
) : InsertLocalUserIdUseCase {

    override fun invoke(uid: String) {
        userPreferences.insertUserId(uid = uid)
    }
}