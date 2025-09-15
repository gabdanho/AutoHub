package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.usecase.MillisToDateUseCase
import com.example.autohub.domain.utils.TimeProvider
import javax.inject.Inject

class MillisToDateUseCaseImpl @Inject constructor(
    private val systemTimeProvider: TimeProvider
) : MillisToDateUseCase {

    override fun invoke(millis: Long): String {
        return systemTimeProvider.millisToDate(millis = millis)
    }
}