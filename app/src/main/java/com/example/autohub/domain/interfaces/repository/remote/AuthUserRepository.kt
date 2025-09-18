package com.example.autohub.domain.interfaces.repository.remote

import com.example.autohub.domain.model.user.User
import com.example.autohub.domain.model.user.UserStatus
import com.example.autohub.domain.model.result.FirebaseResult

/**
 * Репозиторий для работы с авторизацией и данными пользователей Firebase.
 */
interface AuthUserRepository {

    /** Авторизация пользователя по email и паролю. */
    suspend fun loginUser(email: String, password: String): FirebaseResult<Unit>

    /** Регистрация нового пользователя. */
    suspend fun registerUser(email: String, password: String, user: User): FirebaseResult<Unit>

    /** Повторная отправка письма для верификации email. */
    suspend fun resendEmailVerification(email: String, password: String): FirebaseResult<Unit>

    /** Обновление статуса пользователя (Online/Offline). */
    suspend fun changeUserStatus(status: UserStatus)

    /** Получение и обновление FCM токена пользователя. */
    suspend fun getUserToken()

    /** Выход пользователя из аккаунта. */
    suspend fun signOut()

    /** Смена пароля пользователя. */
    suspend fun changePassword(newPassword: String): FirebaseResult<Unit>

    /** Восстановление пароля по email. */
    suspend fun forgotPassword(email: String): FirebaseResult<Unit>

    /** Получение идентификатора текущего пользователя. */
    fun getAuthUserId(): String
}