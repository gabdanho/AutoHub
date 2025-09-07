package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.MessengerRepository
import com.example.autohub.domain.interfaces.usecase.GetCountUnreadMessagesUseCase
import kotlinx.coroutines.flow.Flow

class GetCountUnreadMessagesUseCaseImpl(
    private val messengerRepository: MessengerRepository
) : GetCountUnreadMessagesUseCase {

    override fun invoke(authUserUID: String, buyerUID: String): Flow<Int> {
        return messengerRepository.getCountUnreadMessages(
            authUserUID = authUserUID,
            buyerUID = buyerUID
        )
    }
}