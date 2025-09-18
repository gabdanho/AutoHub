package com.example.autohub.domain.interfaces.repository.local

import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий для отслеживания состояния интернет-соединения.
 */
interface NetworkRepository {

    /**
     * Поток, который эмиттит `true`, если есть интернет,
     * и `false`, если интернет отсутствует.
     */
    fun hasInternetConnection(): Flow<Boolean>
}