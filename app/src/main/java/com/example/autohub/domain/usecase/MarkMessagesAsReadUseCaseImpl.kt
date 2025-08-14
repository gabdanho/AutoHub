package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.firebase.MessengerRepository
import com.example.autohub.domain.interfaces.usecase.MarkMessagesAsReadUseCase

class MarkMessagesAsReadUseCaseImpl(
    private val messengerRepository: MessengerRepository
) : MarkMessagesAsReadUseCase {

    override suspend fun invoke(authUserUID: String, buyerUID: String, messageID: String) {
        messengerRepository.markMessagesAsRead(
            authUserUID = authUserUID,
            buyerUID = buyerUID,
            messageID = messageID
        )
    }
}