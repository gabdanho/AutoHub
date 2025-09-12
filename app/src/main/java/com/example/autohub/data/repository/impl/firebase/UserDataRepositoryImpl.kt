package com.example.autohub.data.repository.impl.firebase

import com.example.autohub.data.firebase.model.safeFirebaseCall
import com.example.autohub.data.firebase.model.user.User
import com.example.autohub.data.firebase.utils.FirebaseStorageUtils
import com.example.autohub.data.mapper.toUserDomain
import com.example.autohub.domain.interfaces.repository.remote.UserDataRepository
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.User as UserDomain
import com.example.autohub.domain.model.result.FirebaseResult
import kotlinx.coroutines.tasks.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val fbFirestore: FirebaseFirestore,
    private val fbAuth: FirebaseAuth,
    private val fbStorageUtils: FirebaseStorageUtils
) : UserDataRepository {

    private val user
        get() = fbAuth.currentUser ?: throw IllegalStateException("User not found")

    override suspend fun getUserData(userUID: String): FirebaseResult<UserDomain> {
        if (userUID.isBlank()) {
            return FirebaseResult.Error.UnknownError("User ID cannot be empty")
        }

        return safeFirebaseCall {
            val snapshot = fbFirestore
                .collection("users")
                .document(userUID)
                .get()
                .await()

            val data = snapshot.toObject(User::class.java)
                ?: throw IllegalStateException("User $userUID not found")
            val mappedData = data.toUserDomain()
            mappedData
        }
    }

    override suspend fun uploadUserProfileImageToFirebase(imageRef: ImageUploadData): FirebaseResult<Unit> {
        return safeFirebaseCall {
            imageRef.bytes?.let {
                val uri = fbStorageUtils.uploadImageToFirebase(
                    bytes = it,
                    path = "users_${user.uid}_profileImage.jpg"
                )
                updateProfileInfo("image", uri)
            }
        }
    }

    override suspend fun updateFirstName(firstName: String): FirebaseResult<Unit> {
        return safeFirebaseCall {
            updateProfileInfo(info = "firstName", value = firstName)
        }
    }

    override suspend fun updateLastName(lastName: String): FirebaseResult<Unit> {
        return safeFirebaseCall {
            updateProfileInfo(info = "lastName", value = lastName)
        }
    }

    override suspend fun updateCity(city: String): FirebaseResult<Unit> {
        return safeFirebaseCall {
            updateProfileInfo(info = "city", value = city)
        }
    }

    private suspend fun updateProfileInfo(info: String, value: String) {
        if (value.isNotBlank()) {
            fbFirestore.collection("users").document(user.uid).update(info, value).await()
        }
    }
}