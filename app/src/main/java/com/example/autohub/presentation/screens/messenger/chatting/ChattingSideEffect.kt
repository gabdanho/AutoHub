package com.example.autohub.presentation.screens.messenger.chatting

sealed class ChattingSideEffect {

    data object ScrollToLastMessage : ChattingSideEffect()

    data object StayInLastMessages : ChattingSideEffect()
}