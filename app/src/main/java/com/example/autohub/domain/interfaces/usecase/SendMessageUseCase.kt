package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.User

interface SendMessageUseCase {
    suspend operator fun invoke(
        sender: User,
        receiver: User,
        text: String
    )
}