package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.ChatConservation
import kotlinx.coroutines.flow.Flow

interface GetBuyersChatsUseCase {
    operator fun invoke(authUserUID: String): Flow<List<ChatConservation>>
}