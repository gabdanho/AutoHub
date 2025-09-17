package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.AuthUserRepository
import com.example.autohub.domain.interfaces.usecase.LoginUserUseCase
import com.example.autohub.domain.model.result.FirebaseResult
import javax.inject.Inject

class LoginUserUseCaseImpl @Inject constructor(
    private val authUserRepository: AuthUserRepository
) : LoginUserUseCase {

    override suspend fun invoke(email: String, password: String): FirebaseResult<Unit> {
        return authUserRepository.loginUser(email = email, password = password)
    }
}