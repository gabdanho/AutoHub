package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.AuthUserRepository
import com.example.autohub.domain.interfaces.usecase.GetUserIdUseCase

class GetUserIdUseCaseImpl(
    private val authUserRepository: AuthUserRepository
) : GetUserIdUseCase {

    override suspend fun invoke() {
        authUserRepository.getUserToken()
    }
}