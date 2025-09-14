package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.UserDataRepository
import com.example.autohub.domain.interfaces.usecase.GetUserDataUseCase
import com.example.autohub.domain.model.user.User
import com.example.autohub.domain.model.result.FirebaseResult

class GetUserDataUseCaseImpl(
    private val userDataRepository: UserDataRepository
) : GetUserDataUseCase {

    override suspend fun invoke(userId: String): FirebaseResult<User> {
        return userDataRepository.getUserData(userId = userId)
    }
}