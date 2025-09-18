package com.example.autohub.presentation.model.user

import kotlinx.serialization.Serializable

/**
 * Модель пользователя для Presentation слоя.
 *
 * @property firstName Имя
 * @property lastName Фамилия
 * @property email Email
 * @property phoneNumber Номер телефона
 * @property city Город
 * @property imageUrl URL аватарки
 * @property status Статус пользователя ([UserStatus])
 * @property localToken Локальный токен
 * @property uid ID пользователя
 */
@Serializable
data class User(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val imageUrl: String = "",
    val status: UserStatus = UserStatus.Offline,
    val localToken: String = "",
    val uid: String = ""
)