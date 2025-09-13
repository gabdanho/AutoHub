package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.MessengerRepository
import com.example.autohub.domain.interfaces.usecase.GetParticipantsChatsUseCase
import com.example.autohub.domain.model.ChatConservation
import kotlinx.coroutines.flow.Flow

class GetParticipantsChatsUseCaseImpl(
    private val messengerRepository: MessengerRepository
) : GetParticipantsChatsUseCase {

    override fun invoke(authUserId: String): Flow<List<ChatConservation>> {
        return messengerRepository.getParticipantsChats(authUserId = authUserId)
    }
}