package com.example.autohub.domain.interfaces.repository.remote

import com.example.autohub.domain.model.chat.ChatConservation
import com.example.autohub.domain.model.chat.Message
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.domain.model.user.User
import com.example.autohub.domain.model.user.UserStatus
import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий для работы с чатами и сообщениями.
 */
interface MessengerRepository {

    /** Отправка сообщения от отправителя получателю. */
    suspend fun sendMessage(
        sender: User,
        receiver: User,
        text: String
    ): FirebaseResult<Unit>

    /** Получение списка сообщений между двумя пользователями. */
    fun getMessages(authUserId: String, participantId: String): Flow<List<Message>>

    /** Получение списка всех чатов пользователя. */
    fun getParticipantsChats(authUserId: String): Flow<List<ChatConservation>>

    /** Получение статуса участника чата. */
    fun getParticipantStatus(participantId: String): Flow<UserStatus>

    /** Подсчёт непрочитанных сообщений от участника чата. */
    fun getCountUnreadMessages(authUserId: String, participantId: String): Flow<Int>

    /** Отметка сообщений как прочитанных. */
    suspend fun markMessagesAsRead(authUserId: String, participantId: String, messageID: String)
}