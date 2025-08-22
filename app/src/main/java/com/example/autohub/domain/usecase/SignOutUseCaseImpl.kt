package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.firebase.AuthUserRepository
import com.example.autohub.domain.interfaces.usecase.SignOutUseCase
import javax.inject.Inject

class SignOutUseCaseImpl @Inject constructor(
    private val authUserRepository: AuthUserRepository
) : SignOutUseCase {

    override suspend fun invoke() {
        authUserRepository.signOut()
    }
}