package com.example.autohub.data.repository.firebase

import com.example.autohub.data.firebase.model.safeFirebaseCall
import com.example.autohub.data.firebase.utils.uploadImageToFirebase
import com.example.autohub.domain.interfaces.repository.firebase.UserDataRepository
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.UserData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import com.example.autohub.domain.model.Result
import com.google.firebase.auth.auth

class UserDataRepositoryImpl : UserDataRepository {

    private val fbStore = Firebase.firestore
    private val fbAuth = Firebase.auth

    private val user get() = fbAuth.currentUser ?: throw IllegalStateException("User not found")

    override suspend fun getUserData(userUID: String): Result<UserData> {
        return safeFirebaseCall {
            val snapshot = fbStore
                .collection("users")
                .document(userUID)
                .get()
                .await()

            snapshot.toObject(UserData::class.java)
                ?: throw IllegalStateException("User $userUID not found")
        }
    }

    override suspend fun uploadUserProfileImageToFirebase(imageRef: ImageUploadData): Result<Boolean> {
        return safeFirebaseCall {
            val uri = uploadImageToFirebase(bytes = imageRef.bytes, path = "users/${user.uid}/profileImage.jpg")

            updateProfileInfo("image", uri)

            true
        }
    }

    override suspend fun updateFirstAnsSecondName(
        firstName: String,
        lastName: String,
    ): Result<Boolean> {
        return safeFirebaseCall {
            updateProfileInfo("firstName", firstName)
            updateProfileInfo("secondName", lastName)
            true
        }
    }

    override suspend fun updateCity(city: String): Result<Boolean> {
        return safeFirebaseCall {
            updateProfileInfo("city", city)
            true
        }
    }

    private suspend fun updateProfileInfo(info: String, value: String) {
        if (value.isNotBlank()) {
            fbStore.collection("users").document(user.uid).update(info, value).await()
        }
    }
}