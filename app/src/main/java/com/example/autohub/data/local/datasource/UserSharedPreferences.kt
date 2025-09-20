package com.example.autohub.data.local.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.autohub.domain.interfaces.repository.local.UserDataSource
import javax.inject.Inject

/**
 * Репозиторий для работы с локальными данными пользователя через [SharedPreferences].
 *
 * @property context Контекст приложения, необходим для получения [SharedPreferences].
 */
class UserSharedPreferences @Inject constructor(
    private val context: Context,
) : UserDataSource {

    /** [SharedPreferences] для хранения данных пользователя. */
    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Сохраняет идентификатор пользователя в локальные настройки.
     *
     * @param uid Идентификатор пользователя
     */
    override fun insertUserId(uid: String) {
        prefs.edit {
            putString(KEY_USER_ID, uid)
        }
    }

    /**
     * Получает сохранённый идентификатор пользователя.
     *
     * @return Идентификатор пользователя, либо `null`, если он не сохранён.
     */
    override fun getUserId(): String? {
        return prefs.getString(KEY_USER_ID, DEFAULT_USER_ID)
            .takeIf { it != DEFAULT_USER_ID && it?.isNotBlank() == true }
    }

    /**
     * Очищает сохранённый идентификатор пользователя.
     */
    override suspend fun clearUserId() {
        prefs.edit { remove(KEY_USER_ID) }
    }

    private companion object {
        /** Имя файла [SharedPreferences]. */
        private const val PREFS_NAME = "user_preferences"
        /** Ключ для хранения идентификатора пользователя. */
        private const val KEY_USER_ID = "user_id"
        /** Значение по умолчанию для идентификатора пользователя. */
        private const val DEFAULT_USER_ID = ""
    }
}