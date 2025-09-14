package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.user.User

interface SendMessageUseCase {
    suspend operator fun invoke(
        sender: User,
        receiver: User,
        text: String
    )
}