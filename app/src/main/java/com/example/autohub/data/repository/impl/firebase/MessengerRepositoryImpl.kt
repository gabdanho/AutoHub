package com.example.autohub.data.repository.impl.firebase

import com.example.autohub.data.firebase.constants.FirestoreCollections.CHATS
import com.example.autohub.data.firebase.constants.FirestoreCollections.CONSERVATIONS
import com.example.autohub.data.firebase.constants.FirestoreCollections.MESSAGES
import com.example.autohub.data.firebase.constants.FirestoreCollections.USERS
import com.example.autohub.data.firebase.constants.FirestoreFields.READ_FIELD
import com.example.autohub.data.firebase.constants.FirestoreFields.RECEIVER_ID_FIELD
import com.example.autohub.data.firebase.constants.FirestoreFields.STATUS_FIELD
import com.example.autohub.data.firebase.constants.FirestoreFields.TIME_MILLIS_FIELD
import com.example.autohub.data.mapper.toMessageDomain
import com.example.autohub.data.mapper.toUserStatusDomain
import com.example.autohub.data.firebase.model.chat.ChatConservation
import com.example.autohub.data.firebase.model.chat.Message
import com.example.autohub.data.firebase.model.user.UserStatus
import com.example.autohub.data.firebase.model.safeFirebaseCall
import com.example.autohub.data.mapper.toChatConservationDomain
import com.example.autohub.domain.interfaces.repository.remote.MessengerRepository
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.domain.model.user.User
import com.example.autohub.domain.model.chat.Message as MessageDomain
import com.example.autohub.domain.utils.TimeProvider
import com.example.autohub.domain.model.user.UserStatus as UserStatusDomain
import com.example.autohub.domain.model.chat.ChatConservation as ChatConservationDomain
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Репозиторий работы с чатами и сообщениями.
 */
class MessengerRepositoryImpl @Inject constructor(
    private val fbFirestore: FirebaseFirestore,
    private val timeProvider: TimeProvider,
) : MessengerRepository {

    /**
     * Отправка сообщения другому пользователю.
     *
     * @param sender Отправитель
     * @param receiver Получатель
     * @param text Текст сообщения
     * @return Результат операции
     */
    override suspend fun sendMessage(
        sender: User,
        receiver: User,
        text: String,
    ): FirebaseResult<Unit> {
        return safeFirebaseCall {
            val timeSend = timeProvider.currentTimeMillis()
            val messageId = buildMessageId(timeSend = timeSend)
            val uniqueChatID = getUniqueId(sender.userId, receiver.userId)
            val textWithoutExtraSpaces = text.trim()
            val message = Message(
                id = messageId,
                senderId = sender.userId,
                receiverId = receiver.userId,
                text = textWithoutExtraSpaces,
                timeMillis = timeSend
            )
            val senderConservation = ChatConservation(
                lastMessage = message.text,
                timeMillis = timeSend,
                userId = receiver.userId,
                name = receiver.createShortName(),
                imageUrl = receiver.imageUrl
            )
            val receiverConservation = ChatConservation(
                lastMessage = message.text,
                timeMillis = timeSend,
                userId = sender.userId,
                name = sender.createShortName(),
                imageUrl = sender.imageUrl
            )

            fbFirestore
                .collection(CHATS)
                .document(uniqueChatID)
                .collection(MESSAGES)
                .document(messageId)
                .set(message)
                .await()

            fbFirestore
                .collection(CONSERVATIONS)
                .document(USERS)
                .collection(sender.userId)
                .document(receiver.userId)
                .set(senderConservation)
                .await()

            fbFirestore
                .collection(CONSERVATIONS)
                .document(USERS)
                .collection(receiver.userId)
                .document(sender.userId)
                .set(receiverConservation)
                .await()
        }
    }

    /**
     * Получение списка сообщений между двумя пользователями.
     *
     * @param authUserId Id авторизованного пользователя
     * @param participantId Id участника чата
     * @return Flow списка сообщений
     */
    override fun getMessages(authUserId: String, participantId: String): Flow<List<MessageDomain>> {
        return callbackFlow {
            val uniqueId = getUniqueId(senderId = authUserId, receiverId = participantId)

            val listener = fbFirestore
                .collection(CHATS)
                .document(uniqueId)
                .collection(MESSAGES)
                .orderBy(TIME_MILLIS_FIELD, Query.Direction.ASCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(cause = error)
                    }
                    if (snapshot != null && !snapshot.isEmpty) {
                        val messages = snapshot.documents.mapNotNull { document ->
                            document.toObject(Message::class.java)?.toMessageDomain()
                        }
                        trySend(element = messages).isSuccess
                    } else {
                        trySend(element = emptyList()).isSuccess
                    }
                }
            awaitClose { listener.remove() }
        }
    }

    /**
     * Получение всех чатов пользователя.
     *
     * @param authUserId Id авторизованного пользователя
     * @return Flow списка Domain-моделей чатов
     */
    override fun getParticipantsChats(authUserId: String): Flow<List<ChatConservationDomain>> {
        return callbackFlow {
            val listener = fbFirestore
                .collection(CONSERVATIONS)
                .document(USERS)
                .collection(authUserId)
                .orderBy(TIME_MILLIS_FIELD, Query.Direction.DESCENDING)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        close(cause = error)
                    }

                    if (value != null && !value.isEmpty) {
                        val chats = value.documents.mapNotNull { document ->
                            document.toObject(ChatConservation::class.java)
                                ?.toChatConservationDomain()
                        }
                        trySend(element = chats).isSuccess
                    } else {
                        trySend(element = emptyList()).isSuccess
                    }
                }
            awaitClose { listener.remove() }
        }
    }

    /**
     * Получение статуса участника чата.
     *
     * @param participantId Id участника
     * @return Flow Domain-статуса пользователя
     */
    override fun getParticipantStatus(participantId: String): Flow<UserStatusDomain> {
        return callbackFlow {
            val listener = fbFirestore
                .collection(USERS)
                .document(participantId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(cause = error)
                        return@addSnapshotListener
                    }

                    if (snapshot != null) {
                        when (snapshot.data?.get(STATUS_FIELD)) {
                            UserStatus.ONLINE.value -> trySend(element = UserStatus.ONLINE.toUserStatusDomain())
                            UserStatus.OFFLINE.value -> trySend(element = UserStatus.OFFLINE.toUserStatusDomain())
                        }
                    }
                }
            awaitClose { listener.remove() }
        }
    }

    /**
     * Получение количества непрочитанных сообщений.
     *
     * @param authUserId Id авторизованного пользователя
     * @param participantId Id участника
     * @return Flow количества непрочитанных сообщений
     */
    override fun getCountUnreadMessages(authUserId: String, participantId: String): Flow<Int> {
        return callbackFlow {
            val uniqueId = getUniqueId(authUserId, participantId)

            val listener = fbFirestore
                .collection(CHATS)
                .document(uniqueId)
                .collection(MESSAGES)
                .whereEqualTo(READ_FIELD, false)
                .whereEqualTo(RECEIVER_ID_FIELD, authUserId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(cause = error)
                        return@addSnapshotListener
                    }

                    val count = snapshot?.size() ?: 0
                    trySend(count)
                }

            awaitClose { listener.remove() }
        }
    }

    /**
     * Отметка сообщения как прочитанного.
     *
     * @param authUserId Id текущего пользователя
     * @param participantId Id участника
     * @param messageID Id сообщения
     */
    override suspend fun markMessagesAsRead(
        authUserId: String,
        participantId: String,
        messageID: String,
    ) {
        val uniqueId = getUniqueId(authUserId, participantId)
        val reference = fbFirestore
            .collection(CHATS)
            .document(uniqueId)
            .collection(MESSAGES)
            .document(messageID)

        val document = reference.get().await()

        if (document.exists()) reference.update(READ_FIELD, true).await()
    }
}

/**
 * Генерация уникального идентификатора чата для двух пользователей.
 *
 * @param senderId Id отправителя
 * @param receiverId Id получателя
 * @return Уникальный идентификатор чата
 */
private fun getUniqueId(senderId: String, receiverId: String): String {
    return listOf(senderId, receiverId).sorted().joinToString("")
}

/**
 * Генерация Id сообщения на основе времени отправки.
 *
 * @param timeSend Время отправки сообщения
 * @return Id сообщения
 */
private fun buildMessageId(timeSend: Long) = "message_${timeSend}"

/**
 * Создание короткого имени пользователя.
 *
 * @receiver Domain-модель пользователя
 * @return Короткое имя пользователя (Имя + первая буква фамилии)
 */
private fun User.createShortName() = "$firstName ${lastName[0]}."