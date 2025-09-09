package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.MessengerRepository
import com.example.autohub.domain.interfaces.usecase.SendMessageUseCase

class SendMessageUseCaseImpl(
    private val messengerRepository: MessengerRepository
) : SendMessageUseCase {

    override suspend fun invoke(senderId: String, receiverId: String, text: String) {
        messengerRepository.sendMessage(
            senderId = senderId,
            receiverId = receiverId,
            text = text
        )
    }
}