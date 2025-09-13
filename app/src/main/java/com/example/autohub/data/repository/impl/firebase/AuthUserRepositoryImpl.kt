package com.example.autohub.data.repository.impl.firebase

import com.example.autohub.data.firebase.constants.FirestoreCollections.USERS
import com.example.autohub.data.firebase.constants.FirestoreFields.LOCAL_TOKEN_FIELD
import com.example.autohub.data.firebase.constants.FirestoreFields.STATUS_FIELD
import com.example.autohub.domain.HandledException
import com.example.autohub.data.mapper.toUserStatusDomain
import com.example.autohub.data.mapper.toUserStatusData
import com.example.autohub.data.firebase.model.user.UserStatus
import com.example.autohub.data.firebase.model.safeFirebaseCall
import com.example.autohub.data.mapper.toUserData
import com.example.autohub.domain.interfaces.repository.remote.AuthUserRepository
import com.example.autohub.domain.model.HandleErrorTag
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.domain.model.User as UserDataDomain
import com.example.autohub.domain.model.UserStatus as UserStatusDomain
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthUserRepositoryImpl @Inject constructor(
    private val fbAuth: FirebaseAuth,
    private val fbStore: FirebaseFirestore,
    private val fbMessaging: FirebaseMessaging
) : AuthUserRepository {

    override suspend fun loginUser(email: String, password: String): FirebaseResult<Unit> {
        return safeFirebaseCall {
            fbAuth.signInWithEmailAndPassword(email, password).await()

            getUserToken()
            changeUserStatus(UserStatus.ONLINE.toUserStatusDomain())
        }
    }

    override suspend fun registerUser(
        email: String,
        password: String,
        user: UserDataDomain,
    ): FirebaseResult<Unit> {
        return safeFirebaseCall {
            fbAuth.createUserWithEmailAndPassword(email, password).await()
            val userId = getAuthUserId()
            val docReference = fbStore.collection(USERS).document(userId)
            val updatedData = user.copy(uid = userId)
            val mappedData = updatedData.toUserData()
            docReference.set(mappedData).await()

            fbAuth.currentUser?.sendEmailVerification()?.await().also {
                signOut()
            }
        }
    }

    override suspend fun resendEmailVerification(email: String, password: String): FirebaseResult<Unit> {
        return safeFirebaseCall {
            fbAuth.signInWithEmailAndPassword(email, password).await()
            val authUser = fbAuth.currentUser ?: throw HandledException(tag = HandleErrorTag.USER_NULL)
            authUser.sendEmailVerification().await().also {
                signOut()
            }
        }
    }

    override suspend fun getUserToken() {
        safeFirebaseCall {
            val fbStoreRef = fbStore.collection(USERS).document(getAuthUserId())
            val token = fbMessaging.token.await()

            fbStoreRef.update(LOCAL_TOKEN_FIELD, token).await()
        }
    }

    override suspend fun signOut() {
        safeFirebaseCall {
            fbAuth.signOut()
        }
    }

    override suspend fun changeUserStatus(status: UserStatusDomain) {
        fbAuth.currentUser?.let {
            fbStore
                .collection(USERS)
                .document(getAuthUserId())
                .update(STATUS_FIELD, status.toUserStatusData().value)
                .await()
        }
    }

    override suspend fun changePassword(newPassword: String): FirebaseResult<Unit> {
        return safeFirebaseCall {
            fbAuth.currentUser?.updatePassword(newPassword)
        }
    }

    override suspend fun forgotPassword(email: String): FirebaseResult<Unit> {
        return safeFirebaseCall {
            fbAuth.sendPasswordResetEmail(email).await()
        }
    }

    override fun getAuthUserId(): String =
        fbAuth.currentUser?.uid ?: throw HandledException(tag = HandleErrorTag.USER_NULL)
}