package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.AuthUserRepository
import com.example.autohub.domain.interfaces.usecase.GetAuthUserIdUseCase

class GetAuthUserIdUseCaseImpl(private val authUserRepository: AuthUserRepository) : GetAuthUserIdUseCase {
    override fun invoke(): String {
        return authUserRepository.getAuthUserUID()
    }
}