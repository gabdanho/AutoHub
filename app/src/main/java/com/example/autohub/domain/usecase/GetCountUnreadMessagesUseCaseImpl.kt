package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.MessengerRepository
import com.example.autohub.domain.interfaces.usecase.GetCountUnreadMessagesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountUnreadMessagesUseCaseImpl @Inject constructor(
    private val messengerRepository: MessengerRepository
) : GetCountUnreadMessagesUseCase {

    override fun invoke(authUserId: String, participantId: String): Flow<Int> {
        return messengerRepository.getCountUnreadMessages(
            authUserId = authUserId,
            participantId = participantId
        )
    }
}