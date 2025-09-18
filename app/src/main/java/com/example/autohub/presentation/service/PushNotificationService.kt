package com.example.autohub.presentation.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Сервис для обработки push-уведомлений через Firebase Cloud Messaging (FCM).
 */
class PushNotificationService : FirebaseMessagingService() {

    /**
     * Вызывается при генерации нового FCM токена.
     *
     * @param token Новый токен устройства
     * Используется для обновления токена на сервере, чтобы устройство продолжало получать уведомления.
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        // Update server
    }

    /**
     * Вызывается при получении нового push-уведомления.
     *
     * @param message Объект [RemoteMessage], содержащий данные уведомления
     * Здесь можно обработать уведомление, показать локальное уведомление или выполнить действие в приложении.
     */
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // Respond to received messages
    }
}