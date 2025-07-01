package com.example.autohub.data.mapper

import com.example.autohub.data.model.ChatInfoDto
import com.example.autohub.domain.model.ChatInfo

fun ChatInfo.toChatInfoDto(): ChatInfoDto {
    return ChatInfoDto(
        name = name,
        image = image,
        lastMessage = lastMessage,
        time = time,
        uid = uid
    )
}

fun ChatInfoDto.toChatInfo(): ChatInfo {
    return ChatInfo(
        name = name,
        image = image,
        lastMessage = lastMessage,
        time = time,
        uid = uid
    )
}