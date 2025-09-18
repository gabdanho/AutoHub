package com.example.autohub.domain.interfaces.usecase

/**
 * UseCase для преобразования миллисекунд в строку даты.
 */
interface MillisToDateUseCase {
    operator fun invoke(millis: Long): String
}