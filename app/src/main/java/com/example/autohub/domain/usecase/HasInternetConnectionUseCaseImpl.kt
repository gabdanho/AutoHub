package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.local.NetworkRepository
import com.example.autohub.domain.interfaces.usecase.HasInternetConnectionUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HasInternetConnectionUseCaseImpl @Inject constructor(
    private val networkRepository: NetworkRepository
) : HasInternetConnectionUseCase {

    override fun invoke(): Flow<Boolean> {
        return networkRepository.hasInternetConnection()
    }
}