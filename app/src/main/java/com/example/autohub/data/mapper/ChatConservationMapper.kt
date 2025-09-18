package com.example.autohub.data.mapper

import com.example.autohub.data.firebase.model.chat.ChatConservation
import com.example.autohub.domain.model.chat.ChatConservation as ChatConservationDomain

/**
 * Конвертация [ChatConservationDomain] в [ChatConservation].
 *
 * @receiver Domain-модель чата
 * @return Data-модель чата
 */
fun ChatConservationDomain.toChatConservationData(): ChatConservation {
    return ChatConservation(
        lastMessage = lastMessage,
        timeMillis = timeMillis,
        userId = userId,
        name = name,
        imageUrl = imageUrl
    )
}

/**
 * Конвертация [ChatConservation] в [ChatConservationDomain].
 *
 * @receiver Data-модель чата
 * @return Domain-модель чата
 */
fun ChatConservation.toChatConservationDomain(): ChatConservationDomain {
    return ChatConservationDomain(
        lastMessage = lastMessage,
        timeMillis = timeMillis,
        userId = userId,
        name = name,
        imageUrl = imageUrl
    )
}