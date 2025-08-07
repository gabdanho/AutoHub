package com.example.autohub.domain.interfaces.repository.firebase

import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.UserData
import com.example.autohub.domain.model.result.FirebaseResult

interface UserDataRepository {

    suspend fun getUserData(userUID: String): FirebaseResult<UserData>

    suspend fun uploadUserProfileImageToFirebase(imageRef: ImageUploadData): FirebaseResult<Boolean>

    suspend fun updateFirstAnsSecondName(firstName: String, lastName: String): FirebaseResult<Boolean>

    suspend fun updateCity(city: String): FirebaseResult<Boolean>
}