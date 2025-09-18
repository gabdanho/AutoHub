package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.chat.Message
import kotlinx.coroutines.flow.Flow

/**
 * UseCase для получения списка сообщений между пользователем и участником чата.
 */
interface GetMessagesUseCase {
    operator fun invoke(authUserId: String, participantId: String): Flow<List<Message>>
}