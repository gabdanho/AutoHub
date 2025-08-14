package com.example.autohub.domain.interfaces.usecase

import com.example.autohub.domain.model.ReceiverData
import com.example.autohub.domain.model.SenderData

interface SendMessageUseCase {
    suspend operator fun invoke(
        sender: SenderData,
        receiver: ReceiverData,
        text: String
    )
}