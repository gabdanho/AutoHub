package com.example.autohub.data.repository.impl.firebase

import android.util.Log
import com.example.autohub.data.mapper.toMessageDomain
import com.example.autohub.data.mapper.toUserStatusDomain
import com.example.autohub.data.firebase.model.chat.ChatConservation
import com.example.autohub.data.firebase.model.chat.Message
import com.example.autohub.data.firebase.model.user.UserStatus
import com.example.autohub.data.firebase.model.safeFirebaseCall
import com.example.autohub.data.mapper.toChatConservationDomain
import com.example.autohub.domain.interfaces.repository.remote.MessengerRepository
import com.example.autohub.domain.model.User
import com.example.autohub.domain.model.Message as MessageDomain
import com.example.autohub.domain.utils.TimeProvider
import com.example.autohub.domain.model.UserStatus as UserStatusDomain
import com.example.autohub.domain.model.ChatConservation as ChatInfoDomain
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
        sender: User,
        receiver: User,
        text: String
    ) {
        safeFirebaseCall {
            val timeSend = timeProvider.currentTimeMillis()
            val formattedData = timeProvider.millisToDate(millis = timeSend)
            val messageId = "message_${timeSend}"
            val uniqueChatID = getUniqueId(sender.uid, receiver.uid)
            val textWithoutExtraSpaces = text.trim()
            val message = Message(
                id = messageId,
                senderUID = sender.uid,
                receiverUID = receiver.uid,
                text = textWithoutExtraSpaces,
                timeMillis = timeSend,
                formattedData = formattedData
            )
            val senderConservation = ChatConservation(
                lastMessage = message.text,
                time = timeSend,
                uid = receiver.uid,
                name = "${receiver.firstName} ${receiver.lastName[0]}.",
                imageUrl = receiver.image
            )
            val receiverConservation = ChatConservation(
                lastMessage = message.text,
                time = timeSend,
                uid = sender.uid,
                name = "${sender.firstName} ${sender.lastName[0]}.",
                imageUrl = sender.image
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
                .collection(sender.uid)
                .document(receiver.uid)
                .set(senderConservation)
                .await()

            fbFirestore
                .collection("conservations")
                .document("users")
                .collection(receiver.uid)
                .document(sender.uid)
                .set(receiverConservation)
                .await()
        }
    }

    override fun getMessages(authUserId: String, participantId: String): Flow<List<MessageDomain>> {
        val uniqueId = getUniqueId(senderUID = authUserId, receiverUID = participantId)

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

    override fun getBuyersChats(authUserUID: String): Flow<List<ChatInfoDomain>> {
        return callbackFlow {
            val listener = fbFirestore
                .collection("conservations")
                .document("users")
                .collection(authUserUID)
                .orderBy("time", Query.Direction.DESCENDING)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        close(cause = error)
                    }

                    if (!value!!.isEmpty) {
                        val chats = value.documents.mapNotNull { document ->
                            document.toObject(ChatConservation::class.java)?.toChatConservationDomain()
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

    override fun getCountUnreadMessages(authUserUID: String, buyerUID: String): Flow<Int> {
        return callbackFlow {
            val uniqueId = getUniqueId(authUserUID, buyerUID)

            val listener = fbFirestore
            .collection("chats")
            .document(uniqueId)
            .collection("messages")
            .whereEqualTo("read", false)
            .whereEqualTo("receiverUID", authUserUID)
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

        if (document.exists()) reference.update("read", true).await()
    }

    private fun getUniqueId(senderUID: String, receiverUID: String): String {
        return listOf(senderUID, receiverUID).sorted().joinToString("")
    }
}