package com.example.autohub.presentation.screens.messenger.chatting


/**
 * Побочные эффекты экрана чата [ChattingScreen].
 */
sealed class ChattingSideEffect {

    /** Скролл к последнему сообщению при открытии чата или отправке сообщения. */
    data object ScrollToLastMessage : ChattingSideEffect()

    /** Удерживание позиции в конце списка сообщений при поступлении новых сообщений. */
    data object StayInLastMessages : ChattingSideEffect()
}