package com.example.autohub.data.repository.impl.firebase

import android.util.Log
import com.example.autohub.data.mapper.toChatInfo
import com.example.autohub.data.mapper.toMessageDomain
import com.example.autohub.data.mapper.toUserStatusDomain
import com.example.autohub.data.firebase.model.chat.ChatInfo
import com.example.autohub.data.firebase.model.chat.Message
import com.example.autohub.data.firebase.model.user.UserStatus
import com.example.autohub.data.firebase.model.safeFirebaseCall
import com.example.autohub.domain.interfaces.repository.firebase.MessengerRepository
import com.example.autohub.domain.model.ChatInfo
import com.example.autohub.domain.model.SenderData
import com.example.autohub.domain.model.Message
import com.example.autohub.domain.model.ReceiverData
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.domain.model.UserStatus
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class MessengerRepositoryImpl : MessengerRepository {

    private val fbFirestore = Firebase.firestore

    override suspend fun sendMessage(
        sender: SenderData,
        receiver: ReceiverData,
        text: String,
        timeSend: Long, // ToDo : System.currentTimeMillis().toString()
    ): FirebaseResult<Boolean> {
        return safeFirebaseCall {
            val messageId = "message_${timeSend}"
            val uniqueChatID = getUniqueId(sender.uid, receiver.uid)
            val message = com.example.autohub.domain.model.Message(
                id = messageId,
                senderUID = sender.uid,
                receiverUID = receiver.uid,
                text = text,
                timeMillis = timeSend
            )

            fbFirestore
                .collection("chats")
                .document(uniqueChatID)
                .collection("messages")
                .document(messageId)
                .set(message)
                .await()

            val senderConservation = ChatInfo(
                name = sender.name,
                image = sender.image,
                lastMessage = message.text,
                time = timeSend,
                uid = receiver.uid
            )
            Firebase.firestore
                .collection("conservations")
                .document("users")
                .collection(sender.uid)
                .document(receiver.uid)
                .set(senderConservation)
                .await()

            val receiverConservation = ChatInfo(
                name = receiver.name,
                image = receiver.image,
                lastMessage = message.text,
                time = timeSend,
                uid = sender.uid
            )
            Firebase.firestore
                .collection("conservations")
                .document("users")
                .collection(receiver.uid)
                .document(sender.uid)
                .set(receiverConservation)
                .await()

            true
        }
    }

    override suspend fun getMessages(authUserUID: String, buyerUID: String): Flow<List<Message>> {
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

    override suspend fun getBuyersChats(authUserUID: String): Flow<List<ChatInfo>> {
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
                            document.toObject(ChatInfo::class.java)?.toChatInfo()
                        }
                        trySend(element = chats).isSuccess
                    } else {
                        trySend(element = emptyList()).isSuccess
                    }
                }
            awaitClose { listener.remove() }
        }
    }

    override suspend fun getBuyerStatus(buyerUID: String): Flow<UserStatus> {
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

    override suspend fun getCountUnreadMessages(authUserUID: String, buyerUID: String): Flow<Int> =
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