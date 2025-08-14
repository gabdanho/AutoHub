package com.example.autohub.presentation.mapper

import com.example.autohub.presentation.model.messenger.ChatInfo
import com.example.autohub.domain.model.ChatInfo as ChatInfoDomain

fun ChatInfo.toChatInfoDomain(): ChatInfoDomain {
    return ChatInfoDomain(
        name = name,
        image = image,
        lastMessage = lastMessage,
        time = time.toLong(),
        uid = uid
    )
}

fun ChatInfoDomain.toChatInfoPresentation(): ChatInfo {
    return ChatInfo(
        name = name,
        image = image,
        lastMessage = lastMessage,
        time = time.toString(),
        uid = uid
    )
}