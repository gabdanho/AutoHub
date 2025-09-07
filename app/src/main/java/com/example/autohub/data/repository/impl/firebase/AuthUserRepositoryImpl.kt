package com.example.autohub.data.repository.impl.firebase

import com.example.autohub.data.mapper.toUserStatusDomain
import com.example.autohub.data.mapper.toUserStatusData
import com.example.autohub.data.firebase.model.user.UserStatus
import com.example.autohub.data.firebase.model.safeFirebaseCall
import com.example.autohub.domain.interfaces.repository.remote.AuthUserRepository
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
            val user = fbAuth.currentUser ?: throw IllegalStateException("User is null after login")

            if (!user.isEmailVerified) fbAuth.signOut()

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
            val userUID = getAuthUserUID()
            val docReference = fbStore.collection("users").document(userUID)
            docReference.set(user).await()

            fbAuth.currentUser?.sendEmailVerification()?.await().also {
                signOut()
            }
        }
    }

    override suspend fun resendEmailVerification(email: String, password: String): FirebaseResult<Unit> {
        return safeFirebaseCall {
            fbAuth.signInWithEmailAndPassword(email, password).await()
            val authUser = fbAuth.currentUser ?: throw IllegalStateException("User not found after login")
            authUser.sendEmailVerification().await().also {
                signOut()
            }
        }
    }

    override suspend fun getUserToken() {
        safeFirebaseCall {
            val fbStoreRef = fbStore.collection("users").document(getAuthUserUID())
            val token = fbMessaging.token.await()

            fbStoreRef.update("localToken", token).await()
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
                .collection("users")
                .document(getAuthUserUID())
                .update("status", status.toUserStatusData().value)
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

    override fun getAuthUserUID(): String =
        fbAuth.currentUser?.uid ?: throw IllegalStateException("Can't get user UID")
}