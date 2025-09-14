package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.AuthUserRepository
import com.example.autohub.domain.interfaces.usecase.ChangeUserStatusUseCase
import com.example.autohub.domain.model.chat.UserStatus

class ChangeUserStatusUseCaseImpl(
    private val authUserRepository: AuthUserRepository
) : ChangeUserStatusUseCase {

    override suspend fun invoke(status: UserStatus) {
        authUserRepository.changeUserStatus(status = status)
    }
}