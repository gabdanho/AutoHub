package com.example.autohub.domain.interfaces.repository.firebase

import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.UserData
import com.example.autohub.domain.model.Result

interface UserDataRepository {

    suspend fun getUserData(userUID: String): Result<UserData>

    suspend fun uploadUserProfileImageToFirebase(imageRef: ImageUploadData): Result<Boolean>

    suspend fun updateFirstAnsSecondName(firstName: String, lastName: String): Result<Boolean>

    suspend fun updateCity(city: String): Result<Boolean>
}