package com.example.autohub.domain.interfaces.usecase

interface MarkMessagesAsReadUseCase {
    suspend operator fun invoke(
        authUserId: String,
        participantId: String,
        messageID: String
    )
}