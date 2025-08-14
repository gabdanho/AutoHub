package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.firebase.AuthUserRepository
import com.example.autohub.domain.interfaces.usecase.LoginUserUseCase
import com.example.autohub.domain.model.result.FirebaseResult

class LoginUserUseCaseImpl(
    private val authUserRepository: AuthUserRepository
) : LoginUserUseCase {

    override suspend fun invoke(email: String, password: String): FirebaseResult<Unit> {
        return authUserRepository.loginUser(email = email, password = password)
    }
}