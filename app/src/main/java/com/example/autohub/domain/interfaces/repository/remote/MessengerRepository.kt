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

    fun getParticipantsChats(authUserId: String): Flow<List<ChatConservation>>

    fun getParticipantStatus(participantId: String): Flow<UserStatus>

    fun getCountUnreadMessages(authUserId: String, participantId: String): Flow<Int>

    suspend fun markMessagesAsRead(authUserId: String, participantId: String, messageID: String)
}