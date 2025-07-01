package com.example.autohub.domain.interfaces.repository.firebase

import com.example.autohub.domain.model.ChatInfo
import com.example.autohub.domain.model.SenderData
import com.example.autohub.domain.model.Message
import com.example.autohub.domain.model.ReceiverData
import com.example.autohub.domain.model.Result
import com.example.autohub.domain.model.UserStatus
import kotlinx.coroutines.flow.Flow

interface MessengerRepository {

    suspend fun sendMessage(
        sender: SenderData,
        receiver: ReceiverData,
        text: String,
        timeSend: Long
    ): Result<Boolean>

    suspend fun getMessages(authUserUID: String, buyerUID: String): Flow<List<Message>>

    suspend fun getBuyersChats(authUserUID: String): Flow<List<ChatInfo>>

    suspend fun getBuyerStatus(buyerUID: String): Flow<UserStatus>

    suspend fun getCountUnreadMessages(authUserUID: String, buyerUID: String): Flow<Int>

    suspend fun markMessagesAsRead(authUserUID: String, buyerUID: String, messageID: String)
}