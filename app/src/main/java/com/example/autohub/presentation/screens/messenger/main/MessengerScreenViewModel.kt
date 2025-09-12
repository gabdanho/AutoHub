package com.example.autohub.presentation.screens.messenger.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autohub.domain.interfaces.usecase.GetAuthUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.GetBuyerStatusUseCase
import com.example.autohub.domain.interfaces.usecase.GetBuyersChatsUseCase
import com.example.autohub.domain.interfaces.usecase.GetCountUnreadMessagesUseCase
import com.example.autohub.domain.interfaces.usecase.GetUserDataUseCase
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.presentation.mapper.toChatConservationPresentation
import com.example.autohub.presentation.mapper.toUserPresentation
import com.example.autohub.presentation.mapper.toUserStatusPresentation
import com.example.autohub.presentation.model.messenger.ChatConservation
import com.example.autohub.presentation.model.messenger.ChatStatus
import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.navigation.Navigator
import com.example.autohub.presentation.navigation.model.graphs.destinations.AccountGraph
import com.example.autohub.presentation.navigation.model.graphs.destinations.AdGraph
import com.example.autohub.presentation.navigation.model.graphs.destinations.MessengerGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessengerScreenViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getBuyerStatusUseCase: GetBuyerStatusUseCase,
    private val getBuyersChatsUseCase: GetBuyersChatsUseCase,
    private val getAuthUserIdUseCase: GetAuthUserIdUseCase,
    private val getCountUnreadMessagesUseCase: GetCountUnreadMessagesUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MessengerScreenUiState())
    val uiState: StateFlow<MessengerScreenUiState> = _uiState.asStateFlow()

    private val _chatsStatus = MutableStateFlow<Map<String, ChatStatus>>(emptyMap())
    val chatsStatus: StateFlow<Map<String, ChatStatus>> = _chatsStatus.asStateFlow()

    private val _chats = MutableStateFlow<List<ChatConservation>>(emptyList())
    val chats: StateFlow<List<ChatConservation>> = _chats.asStateFlow()

    private var statusJobs = mutableMapOf<String, Job>()
    private var chatsJob: Job? = null

    init {
        initChat()
    }

    fun stopListening() {
        statusJobs.values.forEach { it.cancel() }
        chatsJob?.cancel()
        chatsJob = null

        _chatsStatus.value = emptyMap()
        _chats.value = emptyList()
    }

    fun onAdListClick() {
        viewModelScope.launch {
            navigator.navigate(
                destination = AdGraph.AdsMainScreen()
            )
        }
    }

    fun onAccountClick() {
        viewModelScope.launch {
            navigator.navigate(
                destination = AccountGraph.AuthUserAccountScreen
            )
        }
    }

    fun onMessageClick() {
        viewModelScope.launch {
            navigator.navigate(
                destination = MessengerGraph.MessengerScreen
            )
        }
    }

    fun onAnswerClick(uid: String) {
        viewModelScope.launch {
            val user = getUserData(uid = uid).getOrNull()

            if (user != null) {
                navigator.navigate(
                    destination = MessengerGraph.ChattingScreen(
                        participant = user
                    )
                )
            } else {
                _uiState.update { state -> state.copy(errorMessage = "Can't navigate to chat. Cause: can't get user data.") }
            }
        }
    }

    fun clearErrorMessage() {
        _uiState.update { state -> state.copy(errorMessage = null) }
    }

    private suspend fun getUserData(uid: String): Result<User> {
        return runCatching {
            when (val userResult = getUserDataUseCase(userUID = uid)) {
                is FirebaseResult.Success -> {
                    return Result.success(userResult.data.toUserPresentation())
                }

                is FirebaseResult.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            errorMessage = userResult.message
                        )
                    }
                    return Result.failure(exception = Exception(userResult.message))
                }
            }
        }
    }

    private fun startListeningChats() {
        chatsJob?.cancel()

        chatsJob = getBuyersChatsUseCase(authUserUID = getAuthUserIdUseCase())
            .onEach { chats ->
                _chats.value = chats.map { it.toChatConservationPresentation() }

                val authUserId = getAuthUserIdUseCase()
                _chats.value.forEach { chat ->
                    if (!_chatsStatus.value.containsKey(chat.uid)) {
                        startListeningChatStatus(authUserId = authUserId, participantId = chat.uid)
                    }
                }
            }
            .catch { error ->
                _uiState.update { state -> state.copy(errorMessage = "Error to listening chats") }
                Log.e("ParticipantsChats", "Error to  listening chats", error)
            }
            .launchIn(viewModelScope)
    }

    private fun startListeningChatStatus(authUserId: String, participantId: String) {
        val job = combine(
            getBuyerStatusUseCase(buyerUID = participantId),
            getCountUnreadMessagesUseCase(authUserUID = authUserId, buyerUID = participantId)
        ) { status, countUnreadMessages ->
            ChatStatus(
                status = status.toUserStatusPresentation(),
                countUnreadMessages = countUnreadMessages
            )
        }
            .onEach { chatStatus ->
                updateChatInfo(uid = participantId, chatStatus = chatStatus)
            }
            .catch { error ->
                Log.e("ParticipantStatus", "Error listening to user status", error)
            }
            .launchIn(viewModelScope)

        statusJobs[participantId] = job
    }

    private fun initChat() {
        viewModelScope.launch {
            startListeningChats()
        }
    }

    private fun updateChatInfo(uid: String, chatStatus: ChatStatus) {
        _chatsStatus.update { state ->
            val newMap = state.toMutableMap()
            newMap[uid] = chatStatus
            newMap
        }
    }
}