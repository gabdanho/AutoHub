package com.example.autohub.domain.interfaces.repository.remote

import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.User
import com.example.autohub.domain.model.result.FirebaseResult

interface UserDataRepository {

    suspend fun getUserData(userUID: String): FirebaseResult<User>

    suspend fun uploadUserProfileImageToFirebase(imageRef: ImageUploadData): FirebaseResult<Unit>

    suspend fun updateFirstName(firstName: String): FirebaseResult<Unit>

    suspend fun updateLastName(lastName: String): FirebaseResult<Unit>

    suspend fun updateCity(city: String): FirebaseResult<Unit>
}