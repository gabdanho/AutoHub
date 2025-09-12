package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface GetMessagesUseCase {
    operator fun invoke(authUserId: String, participantId: String): Flow<List<Message>>
}