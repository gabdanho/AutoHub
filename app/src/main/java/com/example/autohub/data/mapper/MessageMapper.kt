package com.example.autohub.data.mapper

import com.example.autohub.data.firebase.model.chat.Message
import com.example.autohub.domain.model.Message as MessageDomain

fun Message.toMessageDomain(): MessageDomain {
    return MessageDomain(
        id = id,
        senderUID = senderUID,
        receiverUID = receiverUID,
        text = text,
        timeMillis = timeMillis,
        read = read
    )
}

fun Message.toMessageData(): Message {
    return Message(
        id = id,
        senderUID = senderUID,
        receiverUID = receiverUID,
        text = text,
        timeMillis = timeMillis,
        read = read
    )
}