package com.example.autohub.presentation.mapper

import com.example.autohub.presentation.model.messenger.ParticipantMessageData
import com.example.autohub.domain.model.ParticipantMessageData as ParticipantMessageDataDomain

fun ParticipantMessageData.toUserDomain(): ParticipantMessageDataDomain {
    return ParticipantMessageDataDomain(
        uid = uid,
        name = name,
        image = image
    )
}

fun ParticipantMessageDataDomain.toUserPresentation(): ParticipantMessageData {
    return ParticipantMessageData(
        uid = uid,
        name = name,
        image = image
    )
}