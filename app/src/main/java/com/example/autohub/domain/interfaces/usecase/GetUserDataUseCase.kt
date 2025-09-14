package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.user.User
import com.example.autohub.domain.model.result.FirebaseResult

interface GetUserDataUseCase {
    suspend operator fun invoke(userId: String): FirebaseResult<User>
}