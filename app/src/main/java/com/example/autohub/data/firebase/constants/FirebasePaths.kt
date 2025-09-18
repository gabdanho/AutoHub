package com.example.autohub.data.firebase.constants

/**
 * Пути в Firebase Storage.
 */
object FirebasePaths {

    /** Путь к изображению объявления */
    fun createAdImagePath(reference: String, imageId: Int, ) = "adImages_${reference}_${imageId}.jpg"

    /** Путь к аватарке пользователя */
    fun createProfileImagePath(userId: String) = "users_${userId}_profileImage.jpg"

    /** Ссылка на объявление */
    fun createAdReference(userId: String, timeStamp: Long) = "${userId}_${timeStamp}"
}