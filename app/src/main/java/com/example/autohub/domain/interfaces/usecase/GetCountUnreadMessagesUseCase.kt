package com.example.autohub.domain.interfaces.usecase

import kotlinx.coroutines.flow.Flow

/**
 * UseCase для получения количества непрочитанных сообщений между пользователем и участником.
 */
interface GetCountUnreadMessagesUseCase {
    operator fun invoke(authUserId: String, participantId: String): Flow<Int>
}