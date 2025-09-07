package com.example.autohub.domain.interfaces.repository.local

interface UserPreferencesRepository {

    fun insertUserId(uid: String)

    fun getUserId(): String?
}