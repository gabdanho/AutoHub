package com.example.autohub.domain.interfaces.repository.room

import com.example.autohub.domain.model.result.DbResult

interface TokenRepository {

    suspend fun insertToken(token: String)

    suspend fun getToken(): DbResult<String>
}