package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.MessengerRepository
import com.example.autohub.domain.interfaces.usecase.MarkMessagesAsReadUseCase

class MarkMessagesAsReadUseCaseImpl(
    private val messengerRepository: MessengerRepository
) : MarkMessagesAsReadUseCase {

    override suspend fun invoke(authUserId: String, participantId: String, messageID: String) {
        messengerRepository.markMessagesAsRead(
            authUserId = authUserId,
            participantId = participantId,
            messageID = messageID
        )
    }
}