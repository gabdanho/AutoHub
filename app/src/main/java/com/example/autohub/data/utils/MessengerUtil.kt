package com.example.autohub.data.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.autohub.presentation.model.messenger.BuyerChat
import com.example.autohub.presentation.model.messenger.Message
import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.model.user.UserStatus
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore

fun getUniqueId(sender: String, receiver: String): String {
    return listOf(sender, receiver).sorted().joinToString("")
}

fun sendMessage(
    sender: String,
    receiver: String,
    buyerName: String,
    buyerImage: String,
    text: String
) {
    var senderUserData = User()
    getUserData(sender) { senderUserData = it }

    val messageId = System.currentTimeMillis().toString()
    val message = Message(messageId, sender, receiver, text)
    val uniqueId = getUniqueId(sender, receiver)

    Firebase.firestore
        .collection("chats")
        .document(uniqueId)
        .collection("messages")
        .document(messageId)
        .set(message)
        .addOnCompleteListener { task ->
            Log.d("MESSAGE", "sended: $sender-$receiver-$text")

            val senderConservation = BuyerChat(
                buyerName,
                buyerImage,
                message.text,
                getTimeString(),
                receiver
            )
            Firebase.firestore
                .collection("conservations")
                .document("users")
                .collection(sender)
                .document(receiver)
                .set(senderConservation)

            val receiverConservation = BuyerChat(
                "${senderUserData.firstName} ${senderUserData.secondName}",
                senderUserData.image,
                message.text,
                getTimeString(),
                sender
            )
            Firebase.firestore
                .collection("conservations")
                .document("users")
                .collection(receiver)
                .document(sender)
                .set(receiverConservation)
        }
        .addOnFailureListener { task ->
            Log.e("MESSENGER", "Error: ${task.message}")
        }
}

fun getMessages(buyerUID: String): LiveData<List<Message>> {
    val messages = MutableLiveData<List<Message>>()

    val uniqueId = getUniqueId(getAuthUserUID(), buyerUID)

    Firebase.firestore
        .collection("chats")
        .document(uniqueId)
        .collection("messages")
        .orderBy("timeMillis", Query.Direction.ASCENDING)
        .addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            val messageList = mutableListOf<Message>()

            if (!value!!.isEmpty) {
                value.documents.forEach { document ->
                    val messageModal = document.toObject(Message::class.java)
                    messageList.add(messageModal!!)
                }
            }

            messages.value = messageList
        }

    return messages
}

fun getBuyersChats(): LiveData<List<BuyerChat>> {
    val chats = MutableLiveData<List<BuyerChat>>()

    val userUID = getAuthUserUID()

    Firebase.firestore
        .collection("conservations")
        .document("users")
        .collection(userUID)
        .orderBy("time", Query.Direction.DESCENDING)
        .addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            val chatsList = mutableListOf<BuyerChat>()

            if (!value!!.isEmpty) {
                value.documents.forEach { document ->
                    val chatModal = document.toObject(BuyerChat::class.java)
                    chatsList.add(chatModal!!)
                }
            }

            chats.value = chatsList
        }

    return chats
}

fun getBuyerStatus(uid: String): LiveData<UserStatus> {
    val status = MutableLiveData<UserStatus>()

    Firebase.firestore
        .collection("users")
        .document(uid)
        .addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            if (snapshot != null) {
                when (snapshot.data?.get("status")) {
                    "ONLINE" -> status.value = UserStatus.ONLINE
                    "OFFLINE" -> status.value = UserStatus.OFFLINE
                }
            }
        }

    return status
}

fun getCountUnreadMessages(buyerUID: String, callback: (Int) -> Unit) {
    val uniqueId = getUniqueId(getAuthUserUID(), buyerUID)
    Firebase.firestore
        .collection("chats")
        .document(uniqueId)
        .collection("messages")
        .whereEqualTo("read", false)
        .whereEqualTo("receiver", getAuthUserUID())
        .orderBy("time", Query.Direction.DESCENDING)
        .get()
        .addOnCompleteListener { snapshot ->
            if (!snapshot.isSuccessful) {
                Log.d("MESSAGE", "Error: ${snapshot.exception?.message}")
                callback(0)
            } else {
                val count = snapshot.result.size()
                callback(count)
            }
        }
}

fun markMessagesAsRead(buyerUID: String, messageId: String) {
    val uniqueId = getUniqueId(getAuthUserUID(), buyerUID)
    val reference = Firebase.firestore
        .collection("chats")
        .document(uniqueId)
        .collection("messages")
        .document(messageId)

    reference.get().addOnSuccessListener { document ->
        if (document.exists()) {
            if (document.getBoolean("read") == false) {
                reference.update("read", true)
                    .addOnCompleteListener {
                        Log.d("MESSAGE", "Read changed success")
                    }
                    .addOnFailureListener { error ->
                        Log.d("MESSAGE", "Read not changed. Error: ${error.message}")
                    }
            }
        }
    }
}