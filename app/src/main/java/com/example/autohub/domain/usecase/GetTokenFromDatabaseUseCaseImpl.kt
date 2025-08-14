package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.room.TokenRepository
import com.example.autohub.domain.interfaces.usecase.GetTokenFromDatabaseUseCase
import com.example.autohub.domain.model.result.DbResult

class GetTokenFromDatabaseUseCaseImpl(
    private val tokenRepository: TokenRepository
) : GetTokenFromDatabaseUseCase {

    override suspend fun invoke(): DbResult<String> {
        return tokenRepository.getToken()
    }
}