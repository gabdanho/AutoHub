package com.example.autohub.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.autohub.data.local.dao.TokenRepositoryDao
import com.example.autohub.data.local.model.entity.TokenRepositoryEntity

@Database(entities = [TokenRepositoryEntity::class], version = 1)
abstract class TokenRepositoryDatabase : RoomDatabase() {

    abstract fun tokenDao(): TokenRepositoryDao

    companion object {
        @Volatile
        private var INSTANCE: TokenRepositoryDatabase? = null

        fun getDatabase(context: Context): TokenRepositoryDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    TokenRepositoryDatabase::class.java,
                    "token_repository"
                )
                    .fallbackToDestructiveMigration(false)
                    .build()
                    .also { INSTANCE }
            }
        }
    }
}