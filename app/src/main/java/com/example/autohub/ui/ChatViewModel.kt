package com.example.autohub.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.autohub.data.model.messenger.BuyerChat
import com.example.autohub.data.model.messenger.Message
import com.example.autohub.notifications.FcmApi
import com.example.autohub.notifications.NotificationBody
import com.example.autohub.notifications.SendMessageDto
import com.example.autohub.utils.getBuyersChats
import com.example.autohub.utils.getMessages
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okio.IOException
import retrofit2.Retrofit.*
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class ChatViewModel : ViewModel() {

    val api: FcmApi = Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()

    init {
        viewModelScope.launch {
            Firebase.messaging.subscribeToTopic("chat").await()
        }
    }

    fun getChat(buyerUID: String): LiveData<List<Message>> {
        return getMessages(buyerUID)
    }

    fun getBuyers(): LiveData<List<BuyerChat>> {
        return getBuyersChats()
    }

    fun sendMessage(buyerName: String, messageText: String, buyerToken: String) {
        viewModelScope.launch {
            val messageDto = SendMessageDto(
                to = buyerToken,
                notification = NotificationBody(
                    name = buyerName,
                    body = messageText
                )
            )

            try {
                api.sendMessage(messageDto)
            } catch (e: HttpException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}