package com.example.autohub.data.mapper

import com.example.autohub.data.model.MessageDto
import com.example.autohub.domain.model.Message

fun MessageDto.toMessage(): Message {
    return Message(
        id = id,
        senderUID = senderUID,
        receiverUID = receiverUID,
        text = text,
        timeMillis = timeMillis,
        read = read
    )
}

fun Message.toMessageDto(): MessageDto {
    return MessageDto(
        id = id,
        senderUID = senderUID,
        receiverUID = receiverUID,
        text = text,
        timeMillis = timeMillis,
        read = read
    )
}