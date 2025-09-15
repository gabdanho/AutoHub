package com.example.autohub.domain.interfaces.repository.local

import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    fun hasInternetConnection(): Flow<Boolean>
}