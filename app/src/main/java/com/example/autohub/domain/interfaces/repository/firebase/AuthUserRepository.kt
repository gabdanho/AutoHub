package com.example.autohub.domain.interfaces.repository.firebase

import com.example.autohub.domain.model.Result
import com.example.autohub.domain.model.UserData
import com.example.autohub.domain.model.UserStatus

interface AuthUserRepository {

    suspend fun loginUser(email: String, password: String): Result<Boolean>

    suspend fun registerUser(email: String, password: String, user: UserData): Result<Boolean>

    suspend fun changeUserStatus(status: UserStatus)

    suspend fun getUserToken(): Result<Boolean>
}