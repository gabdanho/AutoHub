package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.AuthUserRepository
import com.example.autohub.domain.interfaces.usecase.ChangeUserStatusUseCase
import com.example.autohub.domain.model.user.UserStatus
import javax.inject.Inject

class ChangeUserStatusUseCaseImpl @Inject constructor(
    private val authUserRepository: AuthUserRepository
) : ChangeUserStatusUseCase {

    override suspend fun invoke(status: UserStatus) {
        authUserRepository.changeUserStatus(status = status)
    }
}