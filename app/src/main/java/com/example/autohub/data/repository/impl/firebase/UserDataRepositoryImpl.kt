package com.example.autohub.data.repository.impl.firebase

import com.example.autohub.data.firebase.constants.FirebasePaths.createProfileImagePath
import com.example.autohub.data.firebase.constants.FirestoreCollections.USERS
import com.example.autohub.data.firebase.constants.FirestoreFields.CITY_FIELD
import com.example.autohub.data.firebase.constants.FirestoreFields.FIRST_NAME_FIELD
import com.example.autohub.data.firebase.constants.FirestoreFields.IMAGE_FIELD
import com.example.autohub.data.firebase.constants.FirestoreFields.LAST_NAME_FIELD
import com.example.autohub.domain.model.result.HandledException
import com.example.autohub.data.firebase.model.safeFirebaseCall
import com.example.autohub.data.firebase.model.user.User
import com.example.autohub.data.firebase.utils.FirebaseStorageUtils
import com.example.autohub.data.mapper.toUserDomain
import com.example.autohub.domain.interfaces.repository.remote.UserDataRepository
import com.example.autohub.domain.model.result.HandleErrorTag
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.user.User as UserDomain
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
        get() = fbAuth.currentUser ?: throw HandledException(tag = HandleErrorTag.USER_NULL)

    override suspend fun getUserData(userId: String): FirebaseResult<UserDomain> {
        return safeFirebaseCall {
            if (userId.isBlank()) {
                throw HandledException(tag = HandleErrorTag.USER_NULL)
            }

            val snapshot = fbFirestore
                .collection(USERS)
                .document(userId)
                .get()
                .await()

            val data = snapshot.toObject(User::class.java)
                ?: throw HandledException(tag = HandleErrorTag.USER_NULL)
            val mappedData = data.toUserDomain()
            mappedData
        }
    }

    override suspend fun uploadUserProfileImageToFirebase(imageRef: ImageUploadData): FirebaseResult<Unit> {
        return safeFirebaseCall {
            imageRef.bytes.let {
                val uri = fbStorageUtils.uploadImageToFirebase(
                    bytes = it,
                    path = createProfileImagePath(userId = user.uid)
                )
                updateProfileInfo(IMAGE_FIELD, uri)
            }
        }
    }

    override suspend fun updateFirstName(firstName: String): FirebaseResult<Unit> {
        return safeFirebaseCall {
            updateProfileInfo(info = FIRST_NAME_FIELD, value = firstName)
        }
    }

    override suspend fun updateLastName(lastName: String): FirebaseResult<Unit> {
        return safeFirebaseCall {
            updateProfileInfo(info = LAST_NAME_FIELD, value = lastName)
        }
    }

    override suspend fun updateCity(city: String): FirebaseResult<Unit> {
        return safeFirebaseCall {
            updateProfileInfo(info = CITY_FIELD, value = city)
        }
    }

    private suspend fun updateProfileInfo(info: String, value: String) {
        if (value.isNotBlank()) {
            fbFirestore.collection(USERS).document(user.uid).update(info, value).await()
        }
    }
}