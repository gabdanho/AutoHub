package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.usecase.ClearUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.SignOutAndClearUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.SignOutUseCase
import javax.inject.Inject

class SignOutAndClearUserIdUseCaseImpl @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val clearUserIdUseCase: ClearUserIdUseCase
) : SignOutAndClearUserIdUseCase {

    override suspend fun invoke() {
        clearUserIdUseCase()
        signOutUseCase()
    }
}