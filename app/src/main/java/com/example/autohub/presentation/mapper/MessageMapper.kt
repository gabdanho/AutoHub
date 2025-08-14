package com.example.autohub.presentation.mapper

import com.example.autohub.presentation.model.messenger.Message
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

fun MessageDomain.toMessagePresentation(): Message {
    return Message(
        id = id,
        senderUID = senderUID,
        receiverUID = receiverUID,
        text = text,
        timeMillis = timeMillis,
        read = read
    )
}