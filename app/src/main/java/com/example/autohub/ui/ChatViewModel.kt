package com.example.autohub.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.autohub.data.BuyerChat
import com.example.autohub.data.Message
import com.example.autohub.utils.getBuyersChats
import com.example.autohub.utils.getMessages

class ChatViewModel : ViewModel() {
    fun getChat(buyerUID: String): LiveData<List<Message>> {
        return getMessages(buyerUID)
    }

    fun getBuyers(): LiveData<List<BuyerChat>> {
        return getBuyersChats()
    }
}