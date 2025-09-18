package com.example.autohub.presentation.screens.ad.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autohub.domain.interfaces.usecase.GetLocalUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.GetUserDataUseCase
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.presentation.mapper.toStringResNamePresentation
import com.example.autohub.presentation.mapper.toUserPresentation
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.StringResNamePresentation
import com.example.autohub.presentation.model.UiMessage
import com.example.autohub.presentation.navigation.Navigator
import com.example.autohub.presentation.navigation.model.graphs.destinations.AccountGraph
import com.example.autohub.presentation.navigation.model.graphs.destinations.MessengerGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для [AdScreen].
 *
 * @param navigator Навигатор для переходов между экранами.
 * @param getUserDataUseCase UseCase для получения данных пользователя по ID.
 * @param getLocalUserIdUseCase UseCase для получения локального ID авторизованного пользователя.
 */
@HiltViewModel
class AdScreenViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getLocalUserIdUseCase: GetLocalUserIdUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdScreenUiState())
    val uiState: StateFlow<AdScreenUiState> = _uiState.asStateFlow()

    private val _callEvent = MutableStateFlow<String?>(null)
    val callEvent: StateFlow<String?> = _callEvent.asStateFlow()

    init {
        viewModelScope.launch {
            getLocalUserIdUseCase().let { uid ->
                _uiState.update { state -> state.copy(authUserId = uid) }
            }
        }
    }

    fun onUserClick() {
        viewModelScope.launch {
            navigator.navigate(
                destination = AccountGraph.AnotherAccountScreen(
                    user = _uiState.value.user
                )
            )
        }
    }

    fun getUserData(uid: String) {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(loadingState = LoadingState.Loading) }

            when (val result = getUserDataUseCase(userId = uid)) {
                is FirebaseResult.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            user = result.data.toUserPresentation(),
                            loadingState = LoadingState.Success
                        )
                    }
                }

                is FirebaseResult.Error.TimeoutError -> {
                    _uiState.update { state ->
                        state.copy(
                            uiMessage = UiMessage(textResName = StringResNamePresentation.ERROR_TIMEOUT_ERROR),
                            loadingState = LoadingState.Error(message = result.message)
                        )
                    }
                }

                is FirebaseResult.Error.HandledError -> {
                    _uiState.update { state ->
                        state.copy(
                            uiMessage = UiMessage(textResName = result.tag.toStringResNamePresentation()),
                            loadingState = LoadingState.Error(message = result.message)
                        )
                    }
                }

                is FirebaseResult.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = LoadingState.Error(message = result.message),
                            uiMessage = UiMessage(textResName = StringResNamePresentation.ERROR_SHOW_USER_DATA)
                        )
                    }
                }
            }
        }
    }

    fun onMessageClick(participantId: String) {
        viewModelScope.launch {
            if (_uiState.value.authUserId != null) {
                val userNav = _uiState.value.user.copy(uid = participantId)

                navigator.navigate(
                    destination = MessengerGraph.ChattingScreen(
                        participant = userNav
                    )
                )
            } else {
                _uiState.update { state -> state.copy(uiMessage = UiMessage(textResName = StringResNamePresentation.INFO_NEED_AUTH)) }
            }
        }
    }

    fun callToUser() {
        viewModelScope.launch {
            if (_uiState.value.authUserId != null) {
                if (_uiState.value.user.phoneNumber.isNotBlank()) {
                    _callEvent.update { _uiState.value.user.phoneNumber }
                } else {
                    _uiState.update { state -> state.copy(uiMessage = UiMessage(textResName = StringResNamePresentation.ERROR_CALL)) }
                }
            } else {
                _uiState.update { state -> state.copy(uiMessage = UiMessage(textResName = StringResNamePresentation.INFO_NEED_AUTH)) }
            }
        }
    }

    fun onBackButtonClick() {
        viewModelScope.launch {
            navigator.navigatePopBackStack()
        }
    }

    fun changeImageIdToShow(value: Int) {
        _uiState.update { state -> state.copy(imageIdToShow = value, isShowImagePager = true) }
    }

    fun clearCallEvent() {
        _callEvent.update { null }
    }

    fun clearMessage() {
        _uiState.update { state -> state.copy(uiMessage = UiMessage()) }
    }

    fun changeIsShowImagePager(value: Boolean) {
        _uiState.update { state -> state.copy(isShowImagePager = value) }
    }
}