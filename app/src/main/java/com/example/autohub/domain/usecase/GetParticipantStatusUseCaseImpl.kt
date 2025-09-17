package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.MessengerRepository
import com.example.autohub.domain.interfaces.usecase.GetParticipantStatusUseCase
import com.example.autohub.domain.model.user.UserStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetParticipantStatusUseCaseImpl @Inject constructor(
    private val messengerRepository: MessengerRepository
) : GetParticipantStatusUseCase {

    override fun invoke(participantId: String): Flow<UserStatus> {
        return messengerRepository.getParticipantStatus(participantId = participantId)
    }
}