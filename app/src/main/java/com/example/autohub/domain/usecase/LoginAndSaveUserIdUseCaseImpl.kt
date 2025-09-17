package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.usecase.GetAuthUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.InsertLocalUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.LoginAndSaveUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.LoginUserUseCase
import com.example.autohub.domain.model.result.FirebaseResult
import javax.inject.Inject

class LoginAndSaveUserIdUseCaseImpl @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val getUserIdUseCase: GetAuthUserIdUseCase,
    private val insertLocalUserIdUseCase: InsertLocalUserIdUseCase
) : LoginAndSaveUserIdUseCase {
    override suspend fun invoke(email: String, password: String): FirebaseResult<Unit> {
        return when (val loginResult = loginUserUseCase(email, password)) {
            is FirebaseResult.Success -> {
                val uid = getUserIdUseCase()
                insertLocalUserIdUseCase(uid)
                FirebaseResult.Success(Unit)
            }
            is FirebaseResult.Error -> {
                loginResult
            }
        }
    }
}