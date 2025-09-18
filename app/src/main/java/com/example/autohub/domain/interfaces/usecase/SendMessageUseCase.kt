package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.domain.model.user.User

/**
 * UseCase для отправки сообщения.
 */
interface SendMessageUseCase {
    suspend operator fun invoke(
        sender: User,
        receiver: User,
        text: String
    ): FirebaseResult<Unit>
}