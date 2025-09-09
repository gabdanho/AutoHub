package com.example.autohub.data.mapper

import com.example.autohub.data.firebase.model.chat.ChatInfo
import com.example.autohub.domain.model.ChatInfo as ChatInfoDomain

fun ChatInfoDomain.toChatInfoData(): ChatInfo {
    return ChatInfo(
        lastMessage = lastMessage,
        time = time.toString(),
        uid = uid
    )
}

fun ChatInfo.toChatInfoDomain(): ChatInfoDomain {
    return ChatInfoDomain(
        lastMessage = lastMessage,
        time = time.toLong(),
        uid = uid
    )
}