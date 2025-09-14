package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.chat.UserStatus

interface ChangeUserStatusUseCase {
    suspend operator fun invoke(status: UserStatus)
}