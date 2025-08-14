package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.result.DbResult

interface GetTokenFromDatabaseUseCase {
    suspend operator fun invoke(): DbResult<String>
}