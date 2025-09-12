package com.example.autohub.data.mapper

import com.example.autohub.data.firebase.model.chat.ChatConservation
import com.example.autohub.domain.model.ChatConservation as ChatConservationDomain

fun ChatConservationDomain.toChatConservationData(): ChatConservation {
    return ChatConservation(
        lastMessage = lastMessage,
        time = time,
        uid = uid,
        name = name,
        imageUrl = imageUrl
    )
}

fun ChatConservation.toChatConservationDomain(): ChatConservationDomain {
    return ChatConservationDomain(
        lastMessage = lastMessage,
        time = time,
        uid = uid,
        name = name,
        imageUrl = imageUrl
    )
}