package com.example.autohub.presentation.screens.account.another

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autohub.domain.interfaces.usecase.GetCurrentUserAdsUseCase
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.presentation.mapper.toCarAdPresentation
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.navigation.Navigator
import com.example.autohub.presentation.navigation.model.graphs.destinations.AdGraph
import com.example.autohub.presentation.navigation.model.graphs.destinations.MessengerGraph
import com.example.autohub.presentation.navigation.model.nav_type.UserNav
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
    private val getCurrentUserAds: GetCurrentUserAdsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnotherAccountScreenUiState())
    val uiState: StateFlow<AnotherAccountScreenUiState> = _uiState.asStateFlow()

    private val _callEvent = MutableStateFlow<String?>(null)
    val callEvent: StateFlow<String?> = _callEvent.asStateFlow()

    fun getUserAds(user: UserNav) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getCurrentUserAds(user.uid)) {
                is FirebaseResult.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            sellerAds = result.data.map { it.toCarAdPresentation() },
                            loadingState = LoadingState.Success
                        )
                    }
                }

                is FirebaseResult.Error -> {
                    _uiState.update { state ->
                        state.copy(loadingState = LoadingState.Error(message = result.message))
                    }
                }
            }
        }
    }

    fun writeToUser() {
        viewModelScope.launch {
            navigator.navigate(destination = MessengerGraph.ChattingScreen)
        }
    }

    fun callToUser(number: String) {
        if (number.isNotBlank()) {
            _callEvent.update { number }
        } else {
            _uiState.update { state -> state.copy(loadingState = LoadingState.Error(message = "Call error")) }
        }
    }

    fun onAdClick(ad: CarAd, user: UserNav) {
        viewModelScope.launch {
            navigator.navigate(
                destination = AdGraph.CurrentAdScreen(
                    carAd = ad,
                    user = user
                )
            )
        }
    }

    fun prevScreen() {
        viewModelScope.launch {
            navigator.navigatePopBackStack()
        }
    }

    fun clearLoadingState() {
        _uiState.update { state ->
            state.copy(loadingState = null)
        }
    }

    fun clearCallEvent() {
        _callEvent.update { null }
    }
}