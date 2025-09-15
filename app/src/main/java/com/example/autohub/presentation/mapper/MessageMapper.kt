package com.example.autohub.presentation.mapper

import com.example.autohub.presentation.model.messenger.Message
import com.example.autohub.domain.model.chat.Message as MessageDomain

fun Message.toUserDomain(): MessageDomain {
    return MessageDomain(
        id = id,
        senderId = senderId,
        receiverId = receiverId,
        text = text,
        timeMillis = timeMillis,
        isRead = isRead,
    )
}

fun MessageDomain.toUserPresentation(): Message {
    return Message(
        id = id,
        senderId = senderId,
        receiverId = receiverId,
        text = text,
        timeMillis = timeMillis,
        isRead = isRead,
    )
}

fun mapListMessageDomainToPresentation(messages: List<MessageDomain>, millisToTime: (Long) -> String): List<Message> {
    return messages.map { message ->
        message.toUserPresentation()
            .copy(timeFormatted = millisToTime(message.timeMillis))
    }
}