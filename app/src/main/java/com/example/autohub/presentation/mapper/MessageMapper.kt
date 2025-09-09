package com.example.autohub.presentation.mapper

import com.example.autohub.presentation.model.messenger.Message
import com.example.autohub.domain.model.Message as MessageDomain

fun Message.toUserDomain(): MessageDomain {
    return MessageDomain(
        id = id,
        senderUID = senderUid,
        receiverUID = receiverUID,
        text = text,
        timeMillis = timeMillis,
        read = read
    )
}

fun MessageDomain.toUserPresentation(): Message {
    return Message(
        id = id,
        senderUid = senderUID,
        receiverUID = receiverUID,
        text = text,
        timeMillis = timeMillis,
        read = read
    )
}