package com.example.autohub.presentation.mapper

import com.example.autohub.presentation.model.messenger.Message
import com.example.autohub.domain.model.chat.Message as MessageDomain

/**
 * Конвертация [Message] в [MessageDomain].
 *
 * @receiver Presentation-модель сообщения
 * @return Domain-модель сообщения
 */
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

/**
 * Конвертация [MessageDomain] в [Message].
 *
 * @receiver Domain-модель сообщения
 * @return Presentation-модель сообщения
 */
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

/**
 * Конвертация списка [MessageDomain] в список [Message] с форматированием времени.
 *
 * @param messages Список доменных сообщений
 * @param millisToTime Функция форматирования времени
 * @return Список Presentation-моделей сообщений
 */
fun mapListMessageDomainToPresentation(messages: List<MessageDomain>, millisToTime: (Long) -> String): List<Message> {
    return messages.map { message ->
        message.toUserPresentation()
            .copy(timeFormatted = millisToTime(message.timeMillis))
    }
}