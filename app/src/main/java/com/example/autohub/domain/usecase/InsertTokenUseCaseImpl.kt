package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.room.TokenRepository
import com.example.autohub.domain.interfaces.usecase.InsertTokenUseCase

class InsertTokenUseCaseImpl(
    private val tokenRepository: TokenRepository
) : InsertTokenUseCase {

    override suspend fun invoke(token: String) {
        tokenRepository.insertToken(token = token)
    }
}