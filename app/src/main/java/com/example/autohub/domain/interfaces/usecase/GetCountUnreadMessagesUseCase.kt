package com.example.autohub.domain.interfaces.usecase

import kotlinx.coroutines.flow.Flow

interface GetCountUnreadMessagesUseCase {
    operator fun invoke(authUserUID: String, buyerUID: String): Flow<Int>
}