package com.example.autohub.data.firebase.constants

object FirebasePaths {

    fun createAdImagePath(
        reference: String,
        imageId: Int,
    ) = "adImages_${reference}_${imageId}.jpg"

    fun createProfileImagePath(
        userId: String
    ) = "users_${userId}_profileImage.jpg"

    fun createAdReference(userId: String, timeStamp: Long) = "${userId}_${timeStamp}"
}