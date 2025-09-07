package com.example.autohub.domain.usecase

import com.example.autohub.data.firebase.model.safeFirebaseCall
import com.example.autohub.domain.interfaces.usecase.GetAuthUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.InsertLocalUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.RegisterAndSaveUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.RegisterUserUseCase
import com.example.autohub.domain.model.User
import com.example.autohub.domain.model.result.FirebaseResult

class RegisterAndSaveUserIdUseCaseImpl(
    private val registerUserUseCase: RegisterUserUseCase,
    private val getUserIdUseCase: GetAuthUserIdUseCase,
    private val insertLocalUserIdUseCase: InsertLocalUserIdUseCase,
) : RegisterAndSaveUserIdUseCase {

    override suspend fun invoke(email: String, password: String, user: User): FirebaseResult<Unit> {
        return safeFirebaseCall {
            when (val registerResult =
                registerUserUseCase(email = email, password = password, user = user)) {
                is FirebaseResult.Success -> {
                    val uid = getUserIdUseCase()
                    insertLocalUserIdUseCase(uid)
                    FirebaseResult.Success(Unit)
                }

                is FirebaseResult.Error -> registerResult
            }
        }
    }
}