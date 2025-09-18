package com.example.autohub.domain.interfaces.usecase

/**
 * UseCase для пометки сообщений как прочитанных.
 */
interface MarkMessagesAsReadUseCase {
    suspend operator fun invoke(
        authUserId: String,
        participantId: String,
        messageID: String
    )
}