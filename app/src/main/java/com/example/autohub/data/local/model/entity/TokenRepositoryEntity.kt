package com.example.autohub.data.local.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TokenRepositoryEntity.TABLE_NAME)
data class TokenRepositoryEntity(
    @PrimaryKey
    val id: Int = 0,
    val tokenKey: String = ""
) {
    companion object {
        const val TABLE_NAME = "token_repo"
    }
}
