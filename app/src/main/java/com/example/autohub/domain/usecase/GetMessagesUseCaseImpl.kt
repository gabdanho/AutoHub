package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.MessengerRepository
import com.example.autohub.domain.interfaces.usecase.GetMessagesUseCase
import com.example.autohub.domain.model.chat.Message
import kotlinx.coroutines.flow.Flow

class GetMessagesUseCaseImpl(
    private val messengerRepository: MessengerRepository
) : GetMessagesUseCase {

    override fun invoke(authUserId: String, participantId: String): Flow<List<Message>> {
        return messengerRepository.getMessages(authUserId = authUserId, participantId = participantId)
    }
}