package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.UserStatus
import kotlinx.coroutines.flow.Flow

interface GetBuyerStatusUseCase {
    operator fun invoke(buyerUID: String): Flow<UserStatus>
}