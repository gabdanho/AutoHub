package com.example.autohub.data.mapper

import com.example.autohub.data.firebase.model.chat.ChatConservation
import com.example.autohub.domain.model.ChatConservation as ChatConservationDomain

fun ChatConservationDomain.toChatConservationData(): ChatConservation {
    return ChatConservation(
        lastMessage = lastMessage,
        timeMillis = time,
        uid = uid,
        name = name,
        imageUrl = imageUrl
    )
}

fun ChatConservation.toChatConservationDomain(): ChatConservationDomain {
    return ChatConservationDomain(
        lastMessage = lastMessage,
        time = timeMillis,
        uid = uid,
        name = name,
        imageUrl = imageUrl
    )
}