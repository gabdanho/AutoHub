package com.example.autohub.presentation.screens.messenger.chatting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autohub.domain.interfaces.usecase.GetAuthUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.GetBuyerStatusUseCase
import com.example.autohub.domain.interfaces.usecase.GetMessagesUseCase
import com.example.autohub.domain.interfaces.usecase.MarkMessagesAsReadUseCase
import com.example.autohub.domain.interfaces.usecase.SendMessageUseCase
import com.example.autohub.presentation.mapper.toUserPresentation
import com.example.autohub.presentation.mapper.toUserStatusPresentation
import com.example.autohub.presentation.model.messenger.Message
import com.example.autohub.presentation.model.user.UserStatus
import com.example.autohub.presentation.navigation.Navigator
import com.example.autohub.presentation.navigation.model.graphs.destinations.AccountGraph
import com.example.autohub.presentation.navigation.model.nav_type.UserNav
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChattingScreenViewModel @Inject constructor(
    private val navigator: Navigator,
    private val markMessagesAsReadUseCase: MarkMessagesAsReadUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val getBuyerStatus: GetBuyerStatusUseCase,
    private val getAuthUserIdUseCase: GetAuthUserIdUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChattingScreenUiState())
    val uiState: StateFlow<ChattingScreenUiState> = _uiState.asStateFlow()

    private val _participantStatus = MutableStateFlow<UserStatus>(UserStatus.Offline)
    val participantStatus: StateFlow<UserStatus> = _participantStatus.asStateFlow()

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private var statusJob: Job? = null
    private var messagesJob: Job? = null

    fun initChat(user: UserNav) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    authUserId = getAuthUserIdUseCase(),
                    participantId = user.uid
                )
            }

            val state = _uiState.value

            startListeningStatus(uid = _uiState.value.participantId)

            startListeningMessages(
                authUserId = state.authUserId,
                participantId = state.participantId
            )
        }
    }

    fun stopListening() {
        statusJob?.cancel()
        messagesJob?.cancel()
        statusJob = null
        messagesJob = null
        _participantStatus.value = UserStatus.Offline
        _messages.value = emptyList()
    }

    fun sendMessage() {
        viewModelScope.launch {
            try {
                val state = _uiState.value

                if (_uiState.value.messageTextValue.isNotBlank()) {
                    sendMessageUseCase(
                        senderId = state.authUserId,
                        receiverId = state.participantId,
                        text = state.messageTextValue
                    )
                }
            } catch (e: Exception) {
                Log.e("ChattingViewModel", "Error sending message", e)
                _uiState.update { state -> state.copy(errorMessage = "Failed to send messages") }
            }
        }
    }

    fun updateMessageTextValue(value: String) {
        _uiState.update { state -> state.copy(messageTextValue = value) }
    }

    fun onParticipantClick(user: UserNav) {
        viewModelScope.launch {
            navigator.navigate(
                destination = AccountGraph.AnotherAccountScreen(user = user)
            )
        }
    }

    fun markMessageAsRead(messageId: String) {
        viewModelScope.launch {
            try {
                val state = _uiState.value

                markMessagesAsReadUseCase(
                    authUserUID = state.authUserId,
                    buyerUID = state.participantId,
                    messageID = messageId
                )
            } catch (e: Exception) {
                Log.e("ChattingViewModel", "Error marking message as read", e)
            }
        }
    }

    private fun startListeningStatus(uid: String) {
        statusJob?.cancel()

        statusJob = getBuyerStatus(buyerUID = uid)
            .onEach { status ->
                _participantStatus.value = status.toUserStatusPresentation()
            }
            .catch { error ->
                Log.e("ParticipantStatus", "Error listening to user status", error)
            }
            .launchIn(viewModelScope)
    }

    private fun startListeningMessages(authUserId: String, participantId: String) {
        messagesJob?.cancel()

        messagesJob = getMessagesUseCase(
            authUserUID = authUserId,
            buyerUID = participantId
        )
            .onEach { messages ->
                _messages.value = messages.map { it.toUserPresentation() }
            }
            .catch { error ->
                Log.e("ParticipantStatus", "Error listening to user status", error)
            }
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        stopListening()
    }
}