package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.firebase.MessengerRepository
import com.example.autohub.domain.interfaces.usecase.SendMessageUseCase
import com.example.autohub.domain.model.ReceiverData
import com.example.autohub.domain.model.SenderData

class SendMessageUseCaseImpl(
    private val messengerRepository: MessengerRepository
) : SendMessageUseCase {

    override suspend fun invoke(sender: SenderData, receiver: ReceiverData, text: String) {
        messengerRepository.sendMessage(
            sender = sender,
            receiver = receiver,
            text = text
        )
    }
}