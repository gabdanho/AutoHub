package com.example.autohub.presentation.screens.messenger.chatting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autohub.domain.interfaces.usecase.GetAuthUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.GetParticipantStatusUseCase
import com.example.autohub.domain.interfaces.usecase.GetMessagesUseCase
import com.example.autohub.domain.interfaces.usecase.GetUserDataUseCase
import com.example.autohub.domain.interfaces.usecase.MarkMessagesAsReadUseCase
import com.example.autohub.domain.interfaces.usecase.MillisToTimeUseCase
import com.example.autohub.domain.interfaces.usecase.SendMessageUseCase
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.presentation.mapper.mapListMessageDomainToPresentation
import com.example.autohub.presentation.mapper.toStringResNamePresentation
import com.example.autohub.presentation.mapper.toUserDomain
import com.example.autohub.presentation.mapper.toUserPresentation
import com.example.autohub.presentation.mapper.toUserStatusPresentation
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.StringResNamePresentation
import com.example.autohub.presentation.model.UiMessage
import com.example.autohub.presentation.model.messenger.ChatLog
import com.example.autohub.presentation.model.messenger.Message
import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.model.user.UserStatus
import com.example.autohub.presentation.navigation.Navigator
import com.example.autohub.presentation.navigation.model.graphs.destinations.AccountGraph
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
    private val getParticipantStatusUseCase: GetParticipantStatusUseCase,
    private val getAuthUserIdUseCase: GetAuthUserIdUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val millisToTimeUseCase: MillisToTimeUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChattingScreenUiState())
    val uiState: StateFlow<ChattingScreenUiState> = _uiState.asStateFlow()

    private val _participantStatus = MutableStateFlow<UserStatus>(UserStatus.Offline)
    val participantStatus: StateFlow<UserStatus> = _participantStatus.asStateFlow()

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _chatSideEffect =
        MutableStateFlow<ChattingSideEffect>(ChattingSideEffect.ScrollToLastMessage)
    val chatSideEffect: StateFlow<ChattingSideEffect> = _chatSideEffect.asStateFlow()

    private var statusJob: Job? = null
    private var messagesJob: Job? = null

    fun initChat(participant: User) {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(loadingState = LoadingState.Loading) }

            when (val authUserResult = getUserDataUseCase(userId = getAuthUserIdUseCase())) {
                is FirebaseResult.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            authUserData = authUserResult.data.toUserPresentation(),
                            participantData = participant,
                            loadingState = LoadingState.Success
                        )
                    }

                    startListeningStatus()
                    startListeningMessages()
                }

                is FirebaseResult.Error.TimeoutError -> {
                    _uiState.update { state ->
                        state.copy(
                            uiMessage = UiMessage(textResName = StringResNamePresentation.ERROR_TIMEOUT_ERROR),
                            loadingState = LoadingState.Error(message = authUserResult.message)
                        )
                    }
                }

                is FirebaseResult.Error.HandledError -> {
                    _uiState.update { state ->
                        state.copy(
                            uiMessage = UiMessage(textResName = authUserResult.tag.toStringResNamePresentation()),
                            loadingState = LoadingState.Error(message = authUserResult.message)
                        )
                    }
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
                    _uiState.update { state -> state.copy(isSendButtonEnabled = false) }

                    when (val result =
                        sendMessageUseCase(
                            sender = state.authUserData.toUserDomain(),
                            receiver = state.participantData.toUserDomain(),
                            text = state.messageTextValue
                        )
                    ) {
                        is FirebaseResult.Success -> {
                            _uiState.update { state ->
                                state.copy(
                                    messageTextValue = "",
                                    isSendButtonEnabled = true
                                )
                            }
                        }

                        is FirebaseResult.Error.HandledError -> {
                            _uiState.update { state ->
                                state.copy(
                                    uiMessage = UiMessage(textResName = result.tag.toStringResNamePresentation()),
                                    isSendButtonEnabled = true
                                )
                            }
                        }

                        is FirebaseResult.Error -> {
                            _uiState.update { state ->
                                state.copy(
                                    uiMessage = UiMessage(details = result.message),
                                    isSendButtonEnabled = true
                                )
                            }
                        }
                    }

                }
                _chatSideEffect.value = ChattingSideEffect.ScrollToLastMessage
            } catch (_: Exception) {
                _uiState.update { state ->
                    state.copy(uiMessage = UiMessage(textResName = StringResNamePresentation.ERROR_SEND_MESSAGE))
                }
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
                    authUserId = state.authUserData.uid,
                    participantId = state.participantData.uid,
                    messageID = messageId
                )
            } catch (e: Exception) {
                Log.e(ChatLog.TAG, ChatLog.ERROR_MARK_MESSAGE, e)
            }
        }
    }

    fun changeChatSideEffect(value: ChattingSideEffect) {
        _chatSideEffect.value = value
    }

    fun clearMessage() {
        _uiState.update { state -> state.copy(uiMessage = UiMessage()) }
    }

    private fun startListeningStatus() {
        statusJob?.cancel()

        statusJob = getParticipantStatusUseCase(participantId = _uiState.value.participantData.uid)
            .onEach { status ->
                _participantStatus.value = status.toUserStatusPresentation()
            }
            .catch { error ->
                Log.e(ChatLog.TAG, ChatLog.ERROR_USER_STATUS, error)
            }
            .launchIn(viewModelScope)
    }

    private fun startListeningMessages() {
        _uiState.update { state -> state.copy(loadingState = LoadingState.Loading) }

        try {
            messagesJob?.cancel()

            val state = _uiState.value

            messagesJob = getMessagesUseCase(
                authUserId = state.authUserData.uid,
                participantId = state.participantData.uid
            )
                .onEach { messages ->
                    _messages.value = mapListMessageDomainToPresentation(
                        messages = messages,
                        millisToTime = millisToTimeUseCase::invoke
                    )
                }
                .catch { error ->
                    Log.e(ChatLog.TAG, ChatLog.ERROR_MESSAGES, error)
                }
                .launchIn(viewModelScope)

            _uiState.update { state -> state.copy(loadingState = LoadingState.Success) }
        } catch (e: Exception) {
            _uiState.update { state -> state.copy(loadingState = LoadingState.Error(message = e.message)) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopListening()
    }
}