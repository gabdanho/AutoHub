package com.example.autohub.domain.interfaces.repository.remote

import com.example.autohub.domain.model.chat.ChatConservation
import com.example.autohub.domain.model.chat.Message
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.domain.model.user.User
import com.example.autohub.domain.model.user.UserStatus
import kotlinx.coroutines.flow.Flow

interface MessengerRepository {

    suspend fun sendMessage(
        sender: User,
        receiver: User,
        text: String
    ): FirebaseResult<Unit>

    fun getMessages(authUserId: String, participantId: String): Flow<List<Message>>

    fun getParticipantsChats(authUserId: String): Flow<List<ChatConservation>>

    fun getParticipantStatus(participantId: String): Flow<UserStatus>

    fun getCountUnreadMessages(authUserId: String, participantId: String): Flow<Int>

    suspend fun markMessagesAsRead(authUserId: String, participantId: String, messageID: String)
}