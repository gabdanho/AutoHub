package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.local.UserDataSource
import com.example.autohub.domain.interfaces.usecase.GetLocalUserIdUseCase
import javax.inject.Inject

class GetLocalUserIdUseCaseImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : GetLocalUserIdUseCase {

    override suspend fun invoke(): String? {
        return userDataSource.getUserId()
    }
}