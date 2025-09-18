package com.example.autohub.domain.interfaces.repository.remote

import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.user.User
import com.example.autohub.domain.model.result.FirebaseResult

/**
 * Репозиторий для работы с данными профиля пользователя.
 */
interface UserDataRepository {

    /** Получение данных пользователя по userId. */
    suspend fun getUserData(userId: String): FirebaseResult<User>

    /** Загрузка изображения профиля пользователя в Firebase Storage. */
    suspend fun uploadUserProfileImageToFirebase(imageRef: ImageUploadData): FirebaseResult<Unit>

    /** Обновление имени пользователя. */
    suspend fun updateFirstName(firstName: String): FirebaseResult<Unit>

    /** Обновление фамилии пользователя. */
    suspend fun updateLastName(lastName: String): FirebaseResult<Unit>

    /** Обновление города пользователя. */
    suspend fun updateCity(city: String): FirebaseResult<Unit>
}