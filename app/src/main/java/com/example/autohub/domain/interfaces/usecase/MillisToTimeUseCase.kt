package com.example.autohub.domain.interfaces.usecase

interface MillisToTimeUseCase {
    operator fun invoke(millis: Long): String
}