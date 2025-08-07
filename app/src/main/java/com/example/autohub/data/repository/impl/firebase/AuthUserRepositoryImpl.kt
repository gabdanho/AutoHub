package com.example.autohub.data.repository.impl.firebase

import com.example.autohub.data.mapper.toUserStatusDomain
import com.example.autohub.data.mapper.toUserStatusData
import com.example.autohub.data.firebase.model.user.UserStatus
import com.example.autohub.data.firebase.model.safeFirebaseCall
import com.example.autohub.domain.interfaces.repository.firebase.AuthUserRepository
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.domain.model.UserData
import com.example.autohub.domain.model.UserStatus
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.tasks.await

class AuthUserRepositoryImpl: AuthUserRepository {

    private val fbAuth = Firebase.auth
    private val fbStore = Firebase.firestore

    override suspend fun loginUser(email: String, password: String): FirebaseResult<Boolean> {
        return safeFirebaseCall {
            fbAuth.signInWithEmailAndPassword(email, password).await()
            val user = fbAuth.currentUser ?: throw IllegalStateException("User is null after login")

            if (!user.isEmailVerified) {
                fbAuth.signOut()
                return@safeFirebaseCall false
            }

            getUserToken()
            changeUserStatus(UserStatus.ONLINE.toUserStatusDomain())

            true
        }
    }

    override suspend fun registerUser(
        email: String,
        password: String,
        user: UserData,
    ): FirebaseResult<Boolean> {
        return safeFirebaseCall {
            fbAuth.createUserWithEmailAndPassword(email, password).await()
            val userUID = getAuthUserUID()
            val docReference = fbStore.collection("users").document(userUID)
            docReference.set(user).await()

            fbAuth.currentUser?.sendEmailVerification()?.await().also {
                fbAuth.signOut()
            }

            true
        }
    }

    override suspend fun getUserToken(): FirebaseResult<Boolean> {
        return safeFirebaseCall {
            val fbStoreRef = fbStore.collection("users").document(getAuthUserUID())
            val token = Firebase.messaging.token.await()

            fbStoreRef.update("localToken", token).await()
            true
        }
    }

    override suspend fun changeUserStatus(status: UserStatus) {
        fbAuth.currentUser?.let {
            fbStore
                .collection("users")
                .document(getAuthUserUID())
                .update("status", status.toUserStatusData().value)
                .await()
        }
    }

    private fun getAuthUserUID(): String =
        fbAuth.currentUser?.uid ?: throw IllegalStateException("Can't get user UID")
}