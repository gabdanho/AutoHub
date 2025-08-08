package com.example.autohub.data.repository.impl.firebase

import com.example.autohub.data.firebase.model.safeFirebaseCall
import com.example.autohub.data.firebase.utils.FirebaseStorageUtils
import com.example.autohub.domain.interfaces.repository.firebase.UserDataRepository
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.UserData
import com.example.autohub.domain.model.result.FirebaseResult
import kotlinx.coroutines.tasks.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val fbStore: FirebaseFirestore,
    private val fbAuth: FirebaseAuth,
    private val fbStorageUtils: FirebaseStorageUtils
) : UserDataRepository {

    private val user
        get() = fbAuth.currentUser ?: throw IllegalStateException("User not found")

    override suspend fun getUserData(userUID: String): FirebaseResult<UserData> {
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

    override suspend fun uploadUserProfileImageToFirebase(imageRef: ImageUploadData) {
        safeFirebaseCall {
            val uri = fbStorageUtils.uploadImageToFirebase(
                bytes = imageRef.bytes,
                path = "users/${user.uid}/profileImage.jpg"
            )
            updateProfileInfo("image", uri)
        }
    }

    override suspend fun updateFirstAnsSecondName(
        firstName: String,
        lastName: String,
    ) {
        safeFirebaseCall {
            fbStore.collection("users").document(user.uid).update(
                mapOf(
                    "firstName" to firstName,
                    "secondName" to lastName
                )
            ).await()
        }
    }

    override suspend fun updateCity(city: String) {
        safeFirebaseCall {
            updateProfileInfo("city", city)
        }
    }

    private suspend fun updateProfileInfo(info: String, value: String) {
        if (value.isNotBlank()) {
            fbStore.collection("users").document(user.uid).update(info, value).await()
        }
    }
}