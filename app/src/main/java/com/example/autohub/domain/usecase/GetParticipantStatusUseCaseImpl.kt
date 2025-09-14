package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.MessengerRepository
import com.example.autohub.domain.interfaces.usecase.GetParticipantStatusUseCase
import com.example.autohub.domain.model.chat.UserStatus
import kotlinx.coroutines.flow.Flow

class GetParticipantStatusUseCaseImpl(
    private val messengerRepository: MessengerRepository
) : GetParticipantStatusUseCase {

    override fun invoke(participantId: String): Flow<UserStatus> {
        return messengerRepository.getParticipantStatus(participantId = participantId)
    }
}