package com.example.autohub.domain.interfaces.usecase

interface SendMessageUseCase {
    suspend operator fun invoke(
        senderId: String,
        receiverId: String,
        text: String
    )
}