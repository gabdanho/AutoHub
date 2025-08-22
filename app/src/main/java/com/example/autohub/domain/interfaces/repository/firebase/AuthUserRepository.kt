package com.example.autohub.domain.interfaces.repository.firebase

import com.example.autohub.domain.model.User
import com.example.autohub.domain.model.UserStatus
import com.example.autohub.domain.model.result.FirebaseResult

interface AuthUserRepository {

    suspend fun loginUser(email: String, password: String): FirebaseResult<Unit>

    suspend fun registerUser(email: String, password: String, user: User): FirebaseResult<Unit>

    suspend fun changeUserStatus(status: UserStatus)

    suspend fun getUserToken()

    suspend fun signOut()

    suspend fun changePassword(newPassword: String): FirebaseResult<Unit>

    fun getAuthUserUID(): String
}