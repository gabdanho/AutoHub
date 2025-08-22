package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.firebase.AuthUserRepository
import com.example.autohub.domain.interfaces.repository.room.TokenRepository
import com.example.autohub.domain.interfaces.usecase.InsertTokenUseCase

class InsertTokenUseCaseImpl(
    private val tokenRepository: TokenRepository,
    private val authUserRepository: AuthUserRepository
) : InsertTokenUseCase {

    override suspend fun invoke() {
        val token = authUserRepository.getAuthUserUID()
        tokenRepository.insertToken(token = token)
    }
}