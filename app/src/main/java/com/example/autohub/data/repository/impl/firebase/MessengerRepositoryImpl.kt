package com.example.autohub.data.repository.impl.firebase

import android.util.Log
import com.example.autohub.data.mapper.toMessageDomain
import com.example.autohub.data.mapper.toUserStatusDomain
import com.example.autohub.data.firebase.model.chat.ChatInfo
import com.example.autohub.data.firebase.model.chat.Message
import com.example.autohub.data.firebase.model.user.UserStatus
import com.example.autohub.data.firebase.model.safeFirebaseCall
import com.example.autohub.data.mapper.toChatInfoDomain
import com.example.autohub.domain.interfaces.repository.remote.MessengerRepository
import com.example.autohub.domain.model.Message as MessageDomain
import com.example.autohub.domain.utils.TimeProvider
import com.example.autohub.domain.model.UserStatus as UserStatusDomain
import com.example.autohub.domain.model.ChatInfo as ChatInfoDomain
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MessengerRepositoryImpl @Inject constructor(
    private val fbFirestore: FirebaseFirestore,
    private val timeProvider: TimeProvider
) : MessengerRepository {

    override suspend fun sendMessage(
        senderId: String,
        receiverId: String,
        text: String
    ) {
        safeFirebaseCall {
            val timeSend = timeProvider.currentTimeMillis()
            val formattedData = timeProvider.millisToDate(millis = timeSend)
            val messageId = "message_${timeSend}"
            val uniqueChatID = getUniqueId(senderId, receiverId)
            val message = MessageDomain(
                id = messageId,
                senderUID = senderId,
                receiverUID = receiverId,
                text = text,
                timeMillis = timeSend,
                formattedData = formattedData
            )
            val senderConservation = ChatInfo(
                lastMessage = message.text,
                time = timeSend.toString(),
                uid = receiverId
            )
            val receiverConservation = ChatInfo(
                lastMessage = message.text,
                time = timeSend.toString(),
                uid = senderId
            )

            fbFirestore
                .collection("chats")
                .document(uniqueChatID)
                .collection("messages")
                .document(messageId)
                .set(message)
                .await()

            fbFirestore
                .collection("conservations")
                .document("users")
                .collection(senderId)
                .document(receiverId)
                .set(senderConservation)
                .await()

            fbFirestore
                .collection("conservations")
                .document("users")
                .collection(receiverId)
                .document(senderId)
                .set(receiverConservation)
                .await()
        }
    }

    override fun getMessages(authUserUID: String, buyerUID: String): Flow<List<MessageDomain>> {
        val uniqueId = getUniqueId(authUserUID, buyerUID)

        return callbackFlow {
            val listener = fbFirestore
                .collection("chats")
                .document(uniqueId)
                .collection("messages")
                .orderBy("timeMillis", Query.Direction.ASCENDING)
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

    override  fun getBuyersChats(authUserUID: String): Flow<List<ChatInfoDomain>> {
        return callbackFlow {
            val listener = fbFirestore
                .collection("conservations")
                .document("users")
                .collection(authUserUID)
                .orderBy("timeMillis", Query.Direction.DESCENDING)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        close(cause = error)
                    }

                    if (!value!!.isEmpty) {
                        val chats = value.documents.mapNotNull { document ->
                            document.toObject(ChatInfo::class.java)?.toChatInfoDomain()
                        }
                        trySend(element = chats).isSuccess
                    } else {
                        trySend(element = emptyList()).isSuccess
                    }
                }
            awaitClose { listener.remove() }
        }
    }

    override fun getBuyerStatus(buyerUID: String): Flow<UserStatusDomain> {
        return callbackFlow {
            val listener = fbFirestore
                .collection("users")
                .document(buyerUID)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(cause = error)
                        return@addSnapshotListener
                    }

                    if (snapshot != null) {
                        when (snapshot.data?.get("status")) {
                            "ONLINE" -> trySend(element = UserStatus.ONLINE.toUserStatusDomain())
                            "OFFLINE" -> trySend(element = UserStatus.OFFLINE.toUserStatusDomain())
                        }
                    }
                }
            awaitClose { listener.remove() }
        }
    }

    override fun getCountUnreadMessages(authUserUID: String, buyerUID: String): Flow<Int> =
        flow {
            val uniqueId = getUniqueId(authUserUID, buyerUID)

            val count = try {
                val snapshot = fbFirestore
                    .collection("chats")
                    .document(uniqueId)
                    .collection("messages")
                    .whereEqualTo("read", false)
                    .whereEqualTo("receiver", authUserUID)
                    .orderBy("timeMillis", Query.Direction.DESCENDING)
                    .get()
                    .await()
                snapshot.size()
            } catch (e: Exception) {
                Log.e("MESSENGER", "Error: ${e.message}")
                0
            }

            emit(value = count)
        }

    override suspend fun markMessagesAsRead(
        authUserUID: String,
        buyerUID: String,
        messageID: String,
    ) {
        val uniqueId = getUniqueId(authUserUID, buyerUID)
        val reference = fbFirestore
            .collection("chats")
            .document(uniqueId)
            .collection("messages")
            .document(messageID)

        val document = reference.get().await()

        if (document.exists()) {
            if (document.getBoolean("read") == false) {
                reference.update("read", true).await()
            }
        }
    }

    private fun getUniqueId(senderUID: String, receiverUID: String): String {
        return listOf(senderUID, receiverUID).sorted().joinToString("")
    }
}