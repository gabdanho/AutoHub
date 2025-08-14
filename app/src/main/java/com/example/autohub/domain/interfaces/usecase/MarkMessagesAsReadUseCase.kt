package com.example.autohub.domain.interfaces.usecase

interface MarkMessagesAsReadUseCase {
    suspend operator fun invoke(
        authUserUID: String,
        buyerUID: String,
        messageID: String
    )
}