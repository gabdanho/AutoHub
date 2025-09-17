package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.user.UserStatus
import kotlinx.coroutines.flow.Flow

interface GetParticipantStatusUseCase {
    operator fun invoke(participantId: String): Flow<UserStatus>
}