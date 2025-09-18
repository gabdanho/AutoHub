package com.example.autohub.domain.model.user

/**
 * Модель пользователя.
 *
 * @property firstName Имя пользователя.
 * @property lastName Фамилия пользователя.
 * @property email Электронная почта пользователя.
 * @property phoneNumber Номер телефона пользователя.
 * @property city Город проживания пользователя.
 * @property imageUrl URL изображения профиля пользователя.
 * @property status Текущий статус пользователя (Online/Offline).
 * @property localToken Локальный токен устройства пользователя (например, для push-уведомлений).
 * @property userId Уникальный идентификатор пользователя.
 */
data class User(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val imageUrl: String = "",
    val status: UserStatus = UserStatus.Offline,
    val localToken: String = "",
    val userId: String = ""
)