package com.example.autohub.presentation.mapper

import com.example.autohub.presentation.model.messenger.ChatConservation
import com.example.autohub.domain.model.ChatConservation as ChatConservationDomain

fun ChatConservation.toChatConservationDomain(): ChatConservationDomain {
    return ChatConservationDomain(
        lastMessage = lastMessage,
        time = time.toLong(),
        uid = uid,
        name = name,
        imageUrl = imageUrl
    )
}

fun ChatConservationDomain.toChatConservationPresentation(): ChatConservation {
    return ChatConservation(
        lastMessage = lastMessage,
        time = time.toString(),
        uid = uid,
        name = name,
        imageUrl = imageUrl
    )
}