package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface GetMessagesUseCase {
    operator fun invoke(authUserUID: String, buyerUID: String): Flow<List<Message>>
}