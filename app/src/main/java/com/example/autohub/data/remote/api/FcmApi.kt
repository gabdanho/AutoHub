package com.example.autohub.data.remote.api

import com.example.autohub.data.firebase.model.chat.SendMessageDto
import retrofit2.http.Body
import retrofit2.http.POST

interface FcmApi {

    @POST("/send")
    suspend fun sendMessage(
        @Body body: SendMessageDto
    )
}