package com.example.autohub.presentation.model

/**
 * Модель сообщения для UI.
 *
 * @property id Уникальный ID сообщения
 * @property textResName Ресурс строки для сообщения
 * @property details Дополнительные детали
 */
data class UiMessage(
    val id: Long = System.currentTimeMillis(),
    val textResName: StringResNamePresentation? = null,
    val details: String? = null,
)