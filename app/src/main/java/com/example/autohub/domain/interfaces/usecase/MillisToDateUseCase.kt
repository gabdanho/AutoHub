package com.example.autohub.domain.interfaces.usecase

interface MillisToDateUseCase {
    operator fun invoke(millis: Long): String
}