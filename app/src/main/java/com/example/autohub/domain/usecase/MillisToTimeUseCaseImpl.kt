package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.usecase.MillisToTimeUseCase
import com.example.autohub.domain.utils.TimeProvider
import javax.inject.Inject

class MillisToTimeUseCaseImpl @Inject constructor(
    private val systemTimeProvider: TimeProvider
) : MillisToTimeUseCase {

    override fun invoke(millis: Long): String {
        return systemTimeProvider.millisToTime(millis = millis)
    }
}