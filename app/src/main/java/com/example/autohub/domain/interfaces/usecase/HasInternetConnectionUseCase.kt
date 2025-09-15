package com.example.autohub.domain.interfaces.usecase

import kotlinx.coroutines.flow.Flow

interface HasInternetConnectionUseCase {
    operator fun invoke(): Flow<Boolean>
}