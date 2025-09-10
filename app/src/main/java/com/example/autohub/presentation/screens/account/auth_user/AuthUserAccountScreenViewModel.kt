package com.example.autohub.presentation.screens.account.auth_user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autohub.domain.interfaces.usecase.GetCurrentUserAdsUseCase
import com.example.autohub.domain.interfaces.usecase.GetLocalUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.GetUserDataUseCase
import com.example.autohub.domain.interfaces.usecase.SignOutAndClearUserIdUseCase
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.presentation.mapper.toCarAdPresentation
import com.example.autohub.presentation.mapper.toUserPresentation
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.navigation.Navigator
import com.example.autohub.presentation.navigation.model.graphs.destinations.AccountGraph
import com.example.autohub.presentation.navigation.model.graphs.destinations.AdGraph
import com.example.autohub.presentation.navigation.model.graphs.destinations.MessengerGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthUserAccountScreenViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getUserData: GetUserDataUseCase,
    private val getCurrentUserAds: GetCurrentUserAdsUseCase,
    private val getToken: GetLocalUserIdUseCase,
    private val signOut: SignOutAndClearUserIdUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUserAccountScreenUiState())
    val uiState: StateFlow<AuthUserAccountScreenUiState> = _uiState.asStateFlow()

    init {
        getUserDataAndAds()
    }

    fun onAdListClick() {
        viewModelScope.launch {
            navigator.navigate(destination = AdGraph.AdsMainScreen())
        }
    }

    fun onAccountClick() {
        viewModelScope.launch {
            navigator.navigate(destination = AccountGraph.AuthUserAccountScreen)
        }
    }

    fun onMessengerClick() {
        viewModelScope.launch {
            navigator.navigate(destination = MessengerGraph.MessengerScreen)
        }
    }

    fun onChangeInfoClick() {
        viewModelScope.launch {
            navigator.navigate(destination = AccountGraph.AccountSettingsScreen)
        }
    }

    fun onSignOutClick() {
        viewModelScope.launch {
            signOut()
            navigator.navigate(destination = AdGraph.AdsMainScreen())
        }
    }

    fun onAdCreateClick() {
        viewModelScope.launch {
            navigator.navigate(destination = AdGraph.CreateAdScreen)
        }
    }

    fun onAdClick(ad: CarAd) {
        viewModelScope.launch {
            navigator.navigate(
                destination = AdGraph.CurrentAdScreen(carAd = ad)
            )
        }
    }

    fun clearLoadingState() {
        _uiState.update { state -> state.copy(loadingState = null) }
    }

    private fun getUserDataAndAds() {
        viewModelScope.launch {
            val uid = getToken()

            uid?.let {
                when (val userResult = getUserData(userUID = uid)) {
                    is FirebaseResult.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                user = userResult.data.toUserPresentation().copy(uid = uid),
                                loadingState = LoadingState.Success
                            )
                        }

                        when (val adsResult = getCurrentUserAds(uid = uid)) {
                            is FirebaseResult.Success -> {
                                _uiState.update { state ->
                                    state.copy(
                                        ads = adsResult.data.map { it.toCarAdPresentation() },
                                        loadingState = LoadingState.Success
                                    )
                                }
                            }

                            is FirebaseResult.Error -> {
                                _uiState.update { state ->
                                    state.copy(
                                        loadingState = LoadingState.Error(message = adsResult.message)
                                    )
                                }
                            }
                        }
                    }

                    is FirebaseResult.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                loadingState = LoadingState.Error(message = userResult.message)
                            )
                        }
                    }
                }
            }
        }
    }
}
