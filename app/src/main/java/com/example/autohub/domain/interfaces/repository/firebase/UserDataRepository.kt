package com.example.autohub.domain.interfaces.repository.firebase

import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.User
import com.example.autohub.domain.model.result.FirebaseResult

interface UserDataRepository {

    suspend fun getUserData(userUID: String): FirebaseResult<User>

    suspend fun uploadUserProfileImageToFirebase(imageRef: ImageUploadData)

    suspend fun updateFirstName(firstName: String)

    suspend fun updateLastName(lastName: String)

    suspend fun updateCity(city: String)
}