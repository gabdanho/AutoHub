package com.example.autohub.domain.interfaces.repository.remote

import com.example.autohub.domain.model.ChatInfo
import com.example.autohub.domain.model.SenderData
import com.example.autohub.domain.model.Message
import com.example.autohub.domain.model.ReceiverData
import com.example.autohub.domain.model.UserStatus
import kotlinx.coroutines.flow.Flow

interface MessengerRepository {

    suspend fun sendMessage(
        sender: SenderData,
        receiver: ReceiverData,
        text: String
    )

    fun getMessages(authUserUID: String, buyerUID: String): Flow<List<Message>>

    fun getBuyersChats(authUserUID: String): Flow<List<ChatInfo>>

    fun getBuyerStatus(buyerUID: String): Flow<UserStatus>

    fun getCountUnreadMessages(authUserUID: String, buyerUID: String): Flow<Int>

    suspend fun markMessagesAsRead(authUserUID: String, buyerUID: String, messageID: String)
}