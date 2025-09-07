package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.AuthUserRepository
import com.example.autohub.domain.interfaces.usecase.GetUserTokenUseCase

class GetUserTokenUseCaseImpl(
    private val authUserRepository: AuthUserRepository
) : GetUserTokenUseCase {

    override suspend fun invoke() {
        authUserRepository.getUserToken()
    }
}