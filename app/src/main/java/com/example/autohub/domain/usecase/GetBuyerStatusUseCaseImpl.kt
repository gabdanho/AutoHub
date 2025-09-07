package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.MessengerRepository
import com.example.autohub.domain.interfaces.usecase.GetBuyerStatusUseCase
import com.example.autohub.domain.model.UserStatus
import kotlinx.coroutines.flow.Flow

class GetBuyerStatusUseCaseImpl(
    private val messengerRepository: MessengerRepository
) : GetBuyerStatusUseCase {

    override fun invoke(buyerUID: String): Flow<UserStatus> {
        return messengerRepository.getBuyerStatus(buyerUID = buyerUID)
    }
}