package com.example.autohub.presentation.screens.account.auth_user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autohub.domain.interfaces.usecase.GetCurrentUserAdsUseCase
import com.example.autohub.domain.interfaces.usecase.GetLocalUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.GetUserDataUseCase
import com.example.autohub.domain.interfaces.usecase.MillisToDateUseCase
import com.example.autohub.domain.interfaces.usecase.SignOutAndClearUserIdUseCase
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.presentation.mapper.mapListCarAdDomainToPresentation
import com.example.autohub.presentation.mapper.toStringResNamePresentation
import com.example.autohub.presentation.mapper.toUserPresentation
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.StringResNamePresentation
import com.example.autohub.presentation.model.UiMessage
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
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getCurrentUserAdsUseCase: GetCurrentUserAdsUseCase,
    private val getLocalUserIdUseCase: GetLocalUserIdUseCase,
    private val signOutAndClearUserIdUseCase: SignOutAndClearUserIdUseCase,
    private val millisToDateUseCase: MillisToDateUseCase,
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
            signOutAndClearUserIdUseCase()
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

    private fun getUserDataAndAds() {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(loadingState = LoadingState.Loading) }

            val uid = getLocalUserIdUseCase()

            uid?.let {
                when (val userResult = getUserDataUseCase(userId = uid)) {

                    is FirebaseResult.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                user = userResult.data.toUserPresentation().copy(uid = uid),
                                loadingState = LoadingState.Success
                            )
                        }

                        when (val adsResult = getCurrentUserAdsUseCase(uid = uid)) {
                            is FirebaseResult.Success -> {
                                _uiState.update { state ->
                                    state.copy(
                                        ads = mapListCarAdDomainToPresentation(
                                            ads = adsResult.data,
                                            millisToDate = millisToDateUseCase::invoke
                                        ),
                                        loadingState = LoadingState.Success
                                    )
                                }
                            }

                            is FirebaseResult.Error.TimeoutError -> {
                                _uiState.update { state ->
                                    state.copy(
                                        uiMessage = UiMessage(textResName = StringResNamePresentation.ERROR_TIMEOUT_ERROR),
                                        loadingState = LoadingState.Error(message = adsResult.message)
                                    )
                                }
                            }

                            is FirebaseResult.Error.HandledError -> {
                                _uiState.update { state ->
                                    state.copy(
                                        uiMessage = UiMessage(textResName = adsResult.tag.toStringResNamePresentation()),
                                        loadingState = LoadingState.Error(message = adsResult.message)
                                    )
                                }
                            }

                            is FirebaseResult.Error -> {
                                _uiState.update { state ->
                                    state.copy(
                                        loadingState = LoadingState.Error(message = adsResult.message),
                                        uiMessage = UiMessage(textResName = StringResNamePresentation.ERROR_SHOW_USER_DATA)
                                    )
                                }
                            }
                        }
                    }

                    is FirebaseResult.Error.TimeoutError -> {
                        _uiState.update { state ->
                            state.copy(
                                uiMessage = UiMessage(textResName = StringResNamePresentation.ERROR_TIMEOUT_ERROR),
                                loadingState = LoadingState.Error(message = userResult.message)
                            )
                        }
                    }

                    is FirebaseResult.Error.HandledError -> {
                        _uiState.update { state ->
                            state.copy(
                                uiMessage = UiMessage(textResName = userResult.tag.toStringResNamePresentation()),
                                loadingState = LoadingState.Error(message = userResult.message)
                            )
                        }
                    }

                    is FirebaseResult.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                loadingState = LoadingState.Error(message = userResult.message),
                                uiMessage = UiMessage(textResName = StringResNamePresentation.ERROR_SHOW_USER_DATA)
                            )
                        }
                    }
                }
            }
        }
    }

    fun clearMessage() {
        _uiState.update { state -> state.copy(uiMessage = UiMessage()) }
    }
}
