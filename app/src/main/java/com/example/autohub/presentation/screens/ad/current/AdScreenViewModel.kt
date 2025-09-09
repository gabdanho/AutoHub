package com.example.autohub.presentation.screens.ad.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autohub.domain.interfaces.usecase.GetUserDataUseCase
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.presentation.mapper.toUserNav
import com.example.autohub.presentation.mapper.toUserPresentation
import com.example.autohub.presentation.model.LoadingState
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

@HiltViewModel
class AdScreenViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getUserDataUseCase: GetUserDataUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdScreenUiState())
    val uiState: StateFlow<AdScreenUiState> = _uiState.asStateFlow()

    private val _callEvent = MutableStateFlow<String?>(null)
    val callEvent: StateFlow<String?> = _callEvent.asStateFlow()

    fun onUserClick() {
        viewModelScope.launch {
            navigator.navigate(
                destination = AccountGraph.AnotherAccountScreen(
                    user = _uiState.value.user.toUserNav()
                )
            )
        }
    }

    fun getUserData(uid: String) {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(loadingState = LoadingState.Loading) }
            when (val result = getUserDataUseCase(userUID = uid)) {
                is FirebaseResult.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            user = result.data.toUserPresentation(),
                            loadingState = LoadingState.Success
                        )
                    }
                }

                is FirebaseResult.Error -> {
                    _uiState.update { state -> state.copy(loadingState = LoadingState.Error(message = result.message)) }
                }
            }
        }
    }

    fun onMessageClick() {
        viewModelScope.launch {
            navigator.navigate(
                destination = MessengerGraph.ChattingScreen(
                    participant = _uiState.value.user.toUserNav()
                )
            )
        }
    }

    fun callToUser() {
        with(_uiState.value.user.phoneNumber) {
            if (this.isNotBlank()) {
                _callEvent.update { this }
            } else {
                _uiState.update { state -> state.copy(loadingState = LoadingState.Error(message = "Call error")) }
            }
        }
    }

    fun onBackButtonClick() {
        viewModelScope.launch {
            navigator.navigatePopBackStack()
        }
    }

    fun changeImageToShow(value: String?) {
        _uiState.update { state -> state.copy(imageToShow = value) }
    }

    fun clearCallEvent() {
        _callEvent.update { null }
    }

    fun clearLoadingState() {
        _uiState.update { state ->
            state.copy(loadingState = null)
        }
    }
}