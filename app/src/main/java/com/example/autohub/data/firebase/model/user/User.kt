package com.example.autohub.data.firebase.model.user

/**
 * Пользователь.
 *
 * @param firstName Имя
 * @param lastName Фамилия
 * @param email Email
 * @param phoneNumber Телефон
 * @param city Город
 * @param imageUrl URL аватарки
 * @param status Статус пользователя
 * @param localToken Локальный токен
 * @param uid ID пользователя
 */
data class User(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val imageUrl: String = "",
    val status: String = "",
    val localToken: String = "",
    val uid: String = ""
)
