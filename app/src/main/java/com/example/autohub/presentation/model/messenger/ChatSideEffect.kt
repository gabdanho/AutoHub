package com.example.autohub.presentation.model.messenger

sealed class ChatSideEffect() {

    data object ScrollToLastMessage : ChatSideEffect()

    data object StayInLastMessages : ChatSideEffect()
}