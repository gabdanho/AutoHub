package com.example.autohub.data.repository.impl.room

import com.example.autohub.data.local.dao.TokenRepositoryDao
import com.example.autohub.data.local.model.safeDbCall
import com.example.autohub.domain.interfaces.repository.room.TokenRepository
import com.example.autohub.domain.model.result.DbResult
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenRepositoryDao: TokenRepositoryDao
): TokenRepository {

    override suspend fun insertToken(token: String) {
        safeDbCall {
            tokenRepositoryDao.updateToken(
                token = token
            )
        }
    }

    override suspend fun getToken(): DbResult<String> {
        return safeDbCall {
            tokenRepositoryDao.getToken()
        }
    }
}