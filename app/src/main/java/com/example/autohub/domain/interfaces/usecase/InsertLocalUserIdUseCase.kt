package com.example.autohub.domain.interfaces.usecase

interface InsertLocalUserIdUseCase {
    operator fun invoke(uid: String)
}