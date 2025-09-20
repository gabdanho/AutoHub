package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.local.UserDataSource
import com.example.autohub.domain.interfaces.usecase.ClearUserIdUseCase
import javax.inject.Inject

class ClearUserIdUseCaseImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : ClearUserIdUseCase {

    override suspend fun invoke() {
        userDataSource.clearUserId()
    }
}