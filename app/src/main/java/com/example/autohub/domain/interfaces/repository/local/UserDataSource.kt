package com.example.autohub.domain.interfaces.repository.local

/**
 * Репозиторий для работы с локальными настройками пользователя.
 */
interface UserDataSource {

    /** Сохраняет идентификатор пользователя. */
    fun insertUserId(uid: String)

    /** Получает сохранённый идентификатор пользователя или null, если отсутствует. */
    fun getUserId(): String?

    /** Очищает сохранённый идентификатор пользователя. */
    suspend fun clearUserId()
}