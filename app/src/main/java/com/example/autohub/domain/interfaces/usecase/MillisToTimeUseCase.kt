package com.example.autohub.domain.interfaces.usecase

/**
 * UseCase для преобразования миллисекунд в строку времени.
 */
interface MillisToTimeUseCase {
    operator fun invoke(millis: Long): String
}