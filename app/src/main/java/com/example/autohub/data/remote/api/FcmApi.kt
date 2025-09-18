package com.example.autohub.data.remote.api

import com.example.autohub.data.firebase.model.chat.SendMessageDto
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * API для отправки сообщений через FCM.
 */
interface FcmApi {

    /**
     * Отправляет сообщение.
     *
     * @param body DTO с информацией для отправки сообщения
     */
    @POST("/send")
    suspend fun sendMessage(
        @Body body: SendMessageDto
    )
}