package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.ChatConservation
import kotlinx.coroutines.flow.Flow

interface GetParticipantsChatsUseCase {
    operator fun invoke(authUserId: String): Flow<List<ChatConservation>>
}