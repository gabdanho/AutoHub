package com.example.autohub.data.mapper

import com.example.autohub.data.firebase.model.chat.Message
import com.example.autohub.domain.model.chat.Message as MessageDomain

/**
 * Конвертация [Message] в [MessageDomain].
 *
 * @receiver Data-модель сообщения
 * @return Domain-модель сообщения
 */
fun Message.toMessageDomain(): MessageDomain {
    return MessageDomain(
        id = id,
        senderId = senderId,
        receiverId = receiverId,
        text = text,
        timeMillis = timeMillis,
        isRead = read,
    )
}

/**
 * Конвертация [MessageDomain] в [Message].
 *
 * @receiver Domain-модель сообщения
 * @return Data-модель сообщения
 */
fun MessageDomain.toMessageData(): Message {
    return Message(
        id = id,
        senderId = senderId,
        receiverId = receiverId,
        text = text,
        timeMillis = timeMillis,
        read = isRead,
    )
}