package com.example.autohub.presentation.mapper

import com.example.autohub.presentation.model.messenger.ChatConservation
import com.example.autohub.domain.model.chat.ChatConservation as ChatConservationDomain

fun ChatConservation.toChatConservationDomain(): ChatConservationDomain {
    return ChatConservationDomain(
        lastMessage = lastMessage,
        timeMillis = timeMillis.toLong(),
        uid = uid,
        name = name,
        imageUrl = imageUrl
    )
}

fun ChatConservationDomain.toChatConservationPresentation(): ChatConservation {
    return ChatConservation(
        lastMessage = lastMessage,
        timeMillis = timeMillis.toString(),
        uid = uid,
        name = name,
        imageUrl = imageUrl
    )
}