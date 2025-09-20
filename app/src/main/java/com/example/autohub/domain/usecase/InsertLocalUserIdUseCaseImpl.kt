package com.example.autohub.domain.usecase

import com.example.autohub.domain.interfaces.repository.local.UserDataSource
import com.example.autohub.domain.interfaces.usecase.InsertLocalUserIdUseCase
import javax.inject.Inject

class InsertLocalUserIdUseCaseImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : InsertLocalUserIdUseCase {

    override fun invoke(uid: String) {
        userDataSource.insertUserId(uid = uid)
    }
}