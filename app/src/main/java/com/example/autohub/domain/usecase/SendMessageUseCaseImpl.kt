package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.MessengerRepository
import com.example.autohub.domain.interfaces.usecase.SendMessageUseCase
import com.example.autohub.domain.model.user.User

class SendMessageUseCaseImpl(
    private val messengerRepository: MessengerRepository
) : SendMessageUseCase {

    override suspend fun invoke(sender: User, receiver: User, text: String) {
        messengerRepository.sendMessage(
            sender = sender,
            receiver = receiver,
            text = text
        )
    }
}