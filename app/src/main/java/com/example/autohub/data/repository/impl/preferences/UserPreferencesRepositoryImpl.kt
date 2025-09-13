package com.example.autohub.data.repository.impl.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.autohub.domain.interfaces.repository.local.UserPreferencesRepository
import javax.inject.Inject
import androidx.core.content.edit

class UserPreferencesRepositoryImpl @Inject constructor(
    private val context: Context,
) : UserPreferencesRepository {

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    override fun insertUserId(uid: String) {
        prefs.edit {
            putString(KEY_USER_ID, uid)
        }
    }

    override fun getUserId(): String? {
        return prefs.getString(KEY_USER_ID, DEFAULT_USER_ID)
            .takeIf { it != DEFAULT_USER_ID && it?.isNotBlank() == true }
    }

    override suspend fun clearUserId() {
        prefs.edit { remove(KEY_USER_ID) }
    }

    private companion object {
        private const val PREFS_NAME = "user_preferences"
        private const val KEY_USER_ID = "user_id"
        private const val DEFAULT_USER_ID = ""
    }
}