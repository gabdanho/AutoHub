package com.example.autohub.domain.interfaces.repository.remote

import com.example.autohub.domain.model.ChatConservation
import com.example.autohub.domain.model.Message
import com.example.autohub.domain.model.User
import com.example.autohub.domain.model.UserStatus
import kotlinx.coroutines.flow.Flow

interface MessengerRepository {

    suspend fun sendMessage(
        sender: User,
        receiver: User,
        text: String
    )

    fun getMessages(authUserId: String, participantId: String): Flow<List<Message>>

    fun getBuyersChats(authUserUID: String): Flow<List<ChatConservation>>

    fun getBuyerStatus(buyerUID: String): Flow<UserStatus>

    fun getCountUnreadMessages(authUserUID: String, buyerUID: String): Flow<Int>

    suspend fun markMessagesAsRead(authUserUID: String, buyerUID: String, messageID: String)
}