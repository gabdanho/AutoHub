package com.example.autohub.presentation.screens.account.another

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autohub.domain.interfaces.usecase.GetCurrentUserAdsUseCase
import com.example.autohub.domain.interfaces.usecase.MillisToDateUseCase
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.presentation.mapper.mapListCarAdDomainToPresentation
import com.example.autohub.presentation.mapper.toStringResNamePresentation
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.StringResNamePresentation
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.navigation.Navigator
import com.example.autohub.presentation.navigation.model.graphs.destinations.AdGraph
import com.example.autohub.presentation.navigation.model.graphs.destinations.MessengerGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnotherAccountScreenViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getCurrentUserAdsUseCase: GetCurrentUserAdsUseCase,
    private val millisToDateUseCase: MillisToDateUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnotherAccountScreenUiState())
    val uiState: StateFlow<AnotherAccountScreenUiState> = _uiState.asStateFlow()

    private val _callEvent = MutableStateFlow<String?>(null)
    val callEvent: StateFlow<String?> = _callEvent.asStateFlow()

    fun getUserAds(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { state -> state.copy(loadingState = LoadingState.Loading) }

            when (val result = getCurrentUserAdsUseCase(user.uid)) {
                is FirebaseResult.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            sellerAds = mapListCarAdDomainToPresentation(
                                ads = result.data,
                                millisToDate = millisToDateUseCase::invoke
                            ),
                            loadingState = LoadingState.Success
                        )
                    }
                }

                is FirebaseResult.Error.TimeoutError -> {
                    _uiState.update { state ->
                        state.copy(
                            message = StringResNamePresentation.ERROR_TIMEOUT_ERROR,
                            loadingState = LoadingState.Error(message = result.message)
                        )
                    }
                }

                is FirebaseResult.Error.HandledError -> {
                    _uiState.update { state ->
                        state.copy(
                            message = result.tag.toStringResNamePresentation(),
                            loadingState = LoadingState.Error(message = result.message)
                        )
                    }
                }

                is FirebaseResult.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = LoadingState.Error(message = result.message),
                            message = StringResNamePresentation.ERROR_SHOW_USER_DATA
                        )
                    }
                }
            }
        }
    }

    fun writeToUser(user: User) {
        viewModelScope.launch {
            navigator.navigate(
                destination = MessengerGraph.ChattingScreen(
                    participant = user
                )
            )
        }
    }

    fun callToUser(number: String) {
        if (number.isNotBlank()) {
            _callEvent.update { number }
        } else {
            _uiState.update { state -> state.copy(message = StringResNamePresentation.ERROR_CALL) }
        }
    }

    fun onAdClick(ad: CarAd) {
        viewModelScope.launch {
            navigator.navigate(
                destination = AdGraph.CurrentAdScreen(carAd = ad)
            )
        }
    }

    fun prevScreen() {
        viewModelScope.launch {
            navigator.navigatePopBackStack()
        }
    }

    fun clearCallEvent() {
        _callEvent.update { null }
    }

    fun clearMessage() {
        _uiState.update { state -> state.copy(message = null) }
    }
}