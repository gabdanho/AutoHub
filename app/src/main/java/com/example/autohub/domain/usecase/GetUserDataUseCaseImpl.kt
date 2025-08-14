package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.firebase.UserDataRepository
import com.example.autohub.domain.interfaces.usecase.GetUserDataUseCase
import com.example.autohub.domain.model.UserData
import com.example.autohub.domain.model.result.FirebaseResult

class GetUserDataUseCaseImpl(
    private val userDataRepository: UserDataRepository
) : GetUserDataUseCase {

    override suspend fun invoke(userUID: String): FirebaseResult<UserData> {
        return userDataRepository.getUserData(userUID = userUID)
    }
}