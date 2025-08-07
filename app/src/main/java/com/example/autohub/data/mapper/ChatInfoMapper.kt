package com.example.autohub.data.mapper

import com.example.autohub.data.firebase.model.chat.ChatInfo
import com.example.autohub.domain.model.ChatInfo as ChatInfoDomain

fun ChatInfo.toChatInfoData(): ChatInfo {
    return ChatInfo(
        name = name,
        image = image,
        lastMessage = lastMessage,
        time = time,
        uid = uid
    )
}

fun ChatInfo.ChatInfoDomain(): ChatInfoDomain {
    return ChatInfoDomain(
        name = name,
        image = image,
        lastMessage = lastMessage,
        time = time.toLong(),
        uid = uid
    )
}