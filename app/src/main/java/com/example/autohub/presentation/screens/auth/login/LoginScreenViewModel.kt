package com.example.autohub.presentation.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autohub.domain.interfaces.usecase.ForgotPasswordUseCase
import com.example.autohub.domain.interfaces.usecase.LoginAndSaveUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.ResendEmailVerificationUseCase
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.presentation.mapper.toStringResNamePresentation
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.StringResNamePresentation
import com.example.autohub.presentation.model.UiMessage
import com.example.autohub.presentation.navigation.Navigator
import com.example.autohub.presentation.navigation.model.graphs.destinations.AdGraph
import com.example.autohub.presentation.navigation.model.graphs.destinations.AuthGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val navigator: Navigator,
    private val loginAndSaveUserIdUseCase: LoginAndSaveUserIdUseCase,
    private val resendEmailVerificationUseCase: ResendEmailVerificationUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginScreenUiState())
    val uiState: StateFlow<LoginScreenUiState> = _uiState.asStateFlow()

    fun changeIsShowPasswordDialog(value: Boolean) {
        _uiState.update { state -> state.copy(isShowPasswordDialog = value) }
    }

    fun changePasswordValue(value: String) {
        _uiState.update { state -> state.copy(passwordValue = value) }
    }

    fun changeEmailValue(value: String) {
        _uiState.update { state -> state.copy(emailValue = value) }
    }

    fun updateEmailForNewPasswordValue(value: String) {
        _uiState.update { state -> state.copy(emailForNewPassword = value) }
    }

    fun updateIsShowSendEmailText(value: Boolean) {
        _uiState.update { state -> state.copy(isShowSendEmailText = value) }
    }

    fun login() {
        _uiState.update { state -> state.copy(loadingState = LoadingState.Loading) }

        viewModelScope.launch {
            when (
                val result = loginAndSaveUserIdUseCase(
                    email = _uiState.value.emailValue,
                    password = _uiState.value.passwordValue
                )
            ) {
                is FirebaseResult.Success -> {
                    navigator.navigate(
                        destination = AdGraph.AdsMainScreen(),
                        navOptions = {
                            popUpTo(0) { inclusive }
                        }
                    )
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
                            uiMessage = UiMessage(
                                textResName = StringResNamePresentation.ERROR_LOGIN,
                                details = result.message
                            ),
                            loadingState = LoadingState.Error(message = result.message)
                        )
                    }
                }
            }
        }
    }

    fun onRegisterButtonClick() {
        viewModelScope.launch {
            navigator.navigate(
                destination = AuthGraph.RegistrationScreen
            )
        }
    }

    fun resendEmailVerification() {
        viewModelScope.launch {
            when (
                val result = resendEmailVerificationUseCase(
                    email = _uiState.value.emailValue,
                    password = _uiState.value.passwordValue
                )
            ) {
                is FirebaseResult.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            uiMessage = UiMessage(textResName = StringResNamePresentation.INFO_VERIF_CODE_SENT)
                        )
                    }
                }

                is FirebaseResult.Error.TimeoutError -> {
                    _uiState.update { state ->
                        state.copy(
                            uiMessage = UiMessage(textResName = StringResNamePresentation.ERROR_TIMEOUT_ERROR)
                        )
                    }
                }

                is FirebaseResult.Error.HandledError -> {
                    _uiState.update { state ->
                        state.copy(
                            uiMessage = UiMessage(textResName = result.tag.toStringResNamePresentation())
                        )
                    }
                }

                is FirebaseResult.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            uiMessage = UiMessage(
                                textResName = StringResNamePresentation.ERROR_TO_SEND_VERIF_CODE,
                                details = result.message
                            ),
                        )
                    }
                }
            }
        }
    }

    fun forgotPassword() {
        viewModelScope.launch {
            when (
                val result = forgotPasswordUseCase(email = _uiState.value.emailForNewPassword)
            ) {
                is FirebaseResult.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            uiMessage = UiMessage(textResName = StringResNamePresentation.INFO_LINK_TO_RESET_PASSWORD_SENT),
                            loadingState = LoadingState.Success,
                            isShowPasswordDialog = false
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
                            uiMessage = UiMessage(
                                textResName = StringResNamePresentation.ERROR_TO_SEND_LINK_TO_RESET_PASSWORD,
                                details = result.message
                            ),
                            loadingState = LoadingState.Error(message = result.message)
                        )
                    }
                }
            }
        }
    }

    fun clearMessage() {
        _uiState.update { state -> state.copy(uiMessage = UiMessage()) }
    }
}