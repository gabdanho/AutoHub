package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.UserData
import com.example.autohub.domain.model.result.FirebaseResult

interface GetUserDataUseCase {
    suspend operator fun invoke(userUID: String): FirebaseResult<UserData>
}