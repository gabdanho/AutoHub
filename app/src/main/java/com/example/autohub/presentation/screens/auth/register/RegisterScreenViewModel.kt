package com.example.autohub.presentation.screens.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autohub.domain.interfaces.usecase.RegisterUserUseCase
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.presentation.mapper.toStringResNamePresentation
import com.example.autohub.presentation.mapper.toUserDomain
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.StringResNamePresentation
import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.navigation.Navigator
import com.example.autohub.presentation.navigation.model.graphs.destinations.AuthGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor(
    private val navigator: Navigator,
    private val registerUserUseCase: RegisterUserUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterScreenUiState())
    val uiState: StateFlow<RegisterScreenUiState> = _uiState.asStateFlow()

    fun onBackClick() {
        viewModelScope.launch {
            navigator.navigate(
                destination = AuthGraph.LoginScreen(),
                navOptions = {
                    popUpTo(0) { inclusive }
                }
            )
        }
    }

    fun updateFirstNameValue(value: String) {
        _uiState.update { state -> state.copy(firstNameValue = value) }
    }

    fun updateLastNameValue(value: String) {
        _uiState.update { state -> state.copy(lastNameValue = value) }
    }

    fun updateEmailValue(value: String) {
        _uiState.update { state -> state.copy(emailValue = value) }
    }

    fun updatePasswordValue(value: String) {
        _uiState.update { state -> state.copy(passwordValue = value) }
    }

    fun updateRepeatPasswordValue(value: String) {
        _uiState.update { state -> state.copy(repeatPasswordValue = value) }
    }

    fun updatePhoneValue(value: String) {
        _uiState.update { state -> state.copy(phoneValue = value) }
    }

    fun updateCityValue(value: String) {
        _uiState.update { state -> state.copy(cityValue = value) }
    }

    fun registerAccount() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isFirstNameError = state.firstNameValue.isBlank(),
                    isLastNameError = state.lastNameValue.isBlank(),
                    isCityError = state.cityValue.isBlank(),
                    isPhoneError = state.phoneValue.isBlank(),
                    isEmailError = state.emailValue.isBlank(),
                    isPasswordError = state.passwordValue.isBlank(),
                    isRepeatPasswordError = state.repeatPasswordValue.isBlank()
                )
            }

            when {
                hasValidationErrors() -> {
                    _uiState.update { it.copy(message = StringResNamePresentation.ERROR_FIELD_AND_OPTIONS_NOT_FILLED_IN) }
                    return@launch
                }

                !isPasswordsMatch() -> {
                    _uiState.update { it.copy(message = StringResNamePresentation.ERROR_PASSWORDS_DONT_MATCH) }
                    return@launch
                }
            }

            val currentState = _uiState.value
            val newUser = formingNewAccount()

            _uiState.update { state -> state.copy(loadingState = LoadingState.Loading) }

            when (
                val result = registerUserUseCase(
                    user = newUser.toUserDomain(),
                    email = currentState.emailValue,
                    password = currentState.passwordValue
                )
            ) {
                is FirebaseResult.Success -> {
                    _uiState.update { state -> state.copy(message = StringResNamePresentation.INFO_REGISTRATION_SUCCESS) }
                    navigator.navigate(
                        destination = AuthGraph.LoginScreen(isShowSendEmailText = true),
                        navOptions = {
                            popUpTo(0) { inclusive }
                        }
                    )
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
                            messageDetails = result.message,
                            message = StringResNamePresentation.ERROR_REGISTRATION_FAILED,
                            loadingState = LoadingState.Error(message = result.message)
                        )
                    }
                }
            }
        }
    }

    fun clearMessage() {
        _uiState.update { state -> state.copy(message = null) }
    }

    fun clearMessageDetails() {
        _uiState.update { state -> state.copy(messageDetails = null) }
    }

    private fun hasValidationErrors(): Boolean {
        val state = _uiState.value

        return state.isFirstNameError || state.isLastNameError ||
                state.isPhoneError || state.isEmailError ||
                state.isCityError || state.isPasswordError ||
                state.isRepeatPasswordError
    }

    private fun formingNewAccount(): User {
        val state = _uiState.value
        return User(
            firstName = state.firstNameValue,
            lastName = state.lastNameValue,
            email = state.emailValue,
            phoneNumber = state.phoneValue,
            city = state.cityValue
        )
    }

    private fun isPasswordsMatch(): Boolean {
        val isMatch = _uiState.value.passwordValue == _uiState.value.repeatPasswordValue
        _uiState.update { state ->
            state.copy(
                isPasswordError = !isMatch,
                isRepeatPasswordError = !isMatch
            )
        }
        return isMatch
    }
}