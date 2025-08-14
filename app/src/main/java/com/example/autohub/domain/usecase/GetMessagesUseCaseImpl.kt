package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.firebase.MessengerRepository
import com.example.autohub.domain.interfaces.usecase.GetMessagesUseCase
import com.example.autohub.domain.model.Message
import kotlinx.coroutines.flow.Flow

class GetMessagesUseCaseImpl(
    private val messengerRepository: MessengerRepository
) : GetMessagesUseCase {

    override fun invoke(authUserUID: String, buyerUID: String): Flow<List<Message>> {
        return messengerRepository.getMessages(authUserUID = authUserUID, buyerUID = buyerUID)
    }
}