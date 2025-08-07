package com.example.autohub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TokenRepositoryDao {

    @Query("SELECT tokenKey FROM token_repo LIMIT 1")
    suspend fun getToken(): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateToken(token: String)
}