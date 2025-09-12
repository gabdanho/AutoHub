package com.example.autohub.presentation.screens.messenger.chatting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autohub.domain.interfaces.usecase.GetAuthUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.GetBuyerStatusUseCase
import com.example.autohub.domain.interfaces.usecase.GetMessagesUseCase
import com.example.autohub.domain.interfaces.usecase.GetUserDataUseCase
import com.example.autohub.domain.interfaces.usecase.MarkMessagesAsReadUseCase
import com.example.autohub.domain.interfaces.usecase.SendMessageUseCase
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.presentation.mapper.toUserDomain
import com.example.autohub.presentation.mapper.toUserPresentation
import com.example.autohub.presentation.mapper.toUserStatusPresentation
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.messenger.Message
import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.model.user.UserStatus
import com.example.autohub.presentation.navigation.Navigator
import com.example.autohub.presentation.navigation.model.graphs.destinations.AccountGraph
import com.example.autohub.presentation.model.messenger.ChatSideEffect
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
    private val getUserDataUseCase: GetUserDataUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChattingScreenUiState())
    val uiState: StateFlow<ChattingScreenUiState> = _uiState.asStateFlow()

    private val _participantStatus = MutableStateFlow<UserStatus>(UserStatus.Offline)
    val participantStatus: StateFlow<UserStatus> = _participantStatus.asStateFlow()

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _chatSideEffect = MutableStateFlow<ChatSideEffect>(ChatSideEffect.ScrollToLastMessage)
    val chatSideEffect: StateFlow<ChatSideEffect> = _chatSideEffect.asStateFlow()

    private var statusJob: Job? = null
    private var messagesJob: Job? = null

    fun initChat(participant: User) {
        viewModelScope.launch {
            when (val authUserResult = getUserDataUseCase(userUID = getAuthUserIdUseCase())) {
                is FirebaseResult.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            authUserData = authUserResult.data.toUserPresentation(),
                            participantData = participant
                        )
                    }

                    startListeningStatus()
                    startListeningMessages()
                }

                is FirebaseResult.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = LoadingState.Error(message = authUserResult.message)
                        )
                    }
                }
            }
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
                        sender = state.authUserData.toUserDomain(),
                        receiver = state.participantData.toUserDomain(),
                        text = state.messageTextValue
                    )
                }
                _chatSideEffect.value = ChatSideEffect.ScrollToLastMessage
                _uiState.update { state -> state.copy(messageTextValue = "") }
            } catch (e: Exception) {
                Log.e("ChattingViewModel", "Error sending message", e)
                _uiState.update { state -> state.copy(errorMessage = "Failed to send messages") }
            }
        }
    }

    fun updateMessageTextValue(value: String) {
        _uiState.update { state -> state.copy(messageTextValue = value) }
    }

    fun onParticipantClick(user: User) {
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
                    authUserUID = state.authUserData.uid,
                    buyerUID = state.participantData.uid,
                    messageID = messageId
                )
            } catch (e: Exception) {
                Log.e("ChattingViewModel", "Error marking message as read", e)
            }
        }
    }

    fun changeChatSideEffect(value: ChatSideEffect) {
        _chatSideEffect.value = value
    }

    private fun startListeningStatus() {
        statusJob?.cancel()

        statusJob = getBuyerStatus(buyerUID = _uiState.value.participantData.uid)
            .onEach { status ->
                _participantStatus.value = status.toUserStatusPresentation()
            }
            .catch { error ->
                Log.e("ParticipantStatus", "Error listening to user status", error)
            }
            .launchIn(viewModelScope)
    }

    private fun startListeningMessages() {
        messagesJob?.cancel()

        val state = _uiState.value

        messagesJob = getMessagesUseCase(
            authUserId = state.authUserData.uid,
            participantId = state.participantData.uid
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