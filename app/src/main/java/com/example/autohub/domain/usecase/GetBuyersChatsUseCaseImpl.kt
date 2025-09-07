package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.remote.MessengerRepository
import com.example.autohub.domain.interfaces.usecase.GetBuyersChatsUseCase
import com.example.autohub.domain.model.ChatInfo
import kotlinx.coroutines.flow.Flow

class GetBuyersChatsUseCaseImpl(
    private val messengerRepository: MessengerRepository
) : GetBuyersChatsUseCase {

    override fun invoke(authUserUID: String): Flow<List<ChatInfo>> {
        return messengerRepository.getBuyersChats(authUserUID = authUserUID)
    }
}