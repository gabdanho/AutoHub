package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.AuthUserRepository
import com.example.autohub.domain.interfaces.usecase.RegisterUserUseCase
import com.example.autohub.domain.model.User
import com.example.autohub.domain.model.result.FirebaseResult

class RegisterUserUseCaseImpl(
    private val authUserRepository: AuthUserRepository
) : RegisterUserUseCase {

    override suspend fun invoke(
        email: String,
        password: String,
        user: User
    ): FirebaseResult<Unit> {
        return authUserRepository.registerUser(email = email, password = password, user = user)
    }
}