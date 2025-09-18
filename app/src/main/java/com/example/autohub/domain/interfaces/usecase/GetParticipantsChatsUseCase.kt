package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.chat.ChatConservation
import kotlinx.coroutines.flow.Flow

/**
 * UseCase для получения списка чатов, в которых участвует пользователь.
 */
interface GetParticipantsChatsUseCase {
    operator fun invoke(authUserId: String): Flow<List<ChatConservation>>
}