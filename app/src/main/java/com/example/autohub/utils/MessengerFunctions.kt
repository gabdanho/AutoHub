package com.example.autohub.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.autohub.data.BuyerChat
import com.example.autohub.data.Message
import com.example.autohub.data.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import java.time.LocalDate

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

    val message = Message(sender, receiver, text)
    val uniqueId = getUniqueId(sender, receiver)

    Firebase.firestore
        .collection("chats")
        .document(uniqueId)
        .collection("messages")
        .document(System.currentTimeMillis().toString())
        .set(message)
        .addOnCompleteListener { task ->
            Log.d("MESSAGE", "sended: $sender-$receiver-$text")

            val senderConservation = BuyerChat(
                buyerName,
                buyerImage,
                message.text,
                getTime(),
                receiver
            )
            Firebase.firestore
                .collection("conservations")
                .document("users")
                .collection(sender)
                .document(receiver)
                .set(senderConservation)

            val receiverConservation = BuyerChat(
                "${senderUserData.firstName} ${senderUserData.secondName}.",
                senderUserData.image,
                message.text,
                getTime(),
                sender
            )
            Firebase.firestore
                .collection("conservations")
                .document("users")
                .collection(receiver)
                .document(sender)
                .set(receiverConservation)
        }
}

fun getMessages(buyerUID: String): LiveData<List<Message>> {
    val messages = MutableLiveData<List<Message>>()

    val uniqueId = getUniqueId(getAuthUserUID(), buyerUID)

    Firebase.firestore
        .collection("chats")
        .document(uniqueId)
        .collection("messages")
        .orderBy("time", Query.Direction.ASCENDING)
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