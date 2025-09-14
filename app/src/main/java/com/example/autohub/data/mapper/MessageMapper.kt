package com.example.autohub.data.mapper

import com.example.autohub.data.firebase.model.chat.Message
import com.example.autohub.domain.model.chat.Message as MessageDomain

fun Message.toMessageDomain(): MessageDomain {
    return MessageDomain(
        id = id,
        senderId = senderId,
        receiverId = receiverId,
        text = text,
        timeMillis = timeMillis,
        isRead = read,
        formattedData = formattedData
    )
}

fun MessageDomain.toMessageData(): Message {
    return Message(
        id = id,
        senderId = senderId,
        receiverId = receiverId,
        text = text,
        timeMillis = timeMillis,
        read = isRead,
        formattedData = formattedData
    )
}