package com.example.autohub.presentation.screens.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autohub.domain.interfaces.usecase.RegisterUserUseCase
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.presentation.mapper.toStringResNamePresentation
import com.example.autohub.presentation.mapper.toUserDomain
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.StringResNamePresentation
import com.example.autohub.presentation.model.UiMessage
import com.example.autohub.presentation.model.user.User
import com.example.autohub.presentation.navigation.Navigator
import com.example.autohub.presentation.navigation.model.graphs.destinations.AuthGraph
import com.example.autohub.presentation.utils.isNameValid
import com.example.autohub.presentation.utils.isPasswordValid
import com.example.autohub.presentation.utils.isValidCity
import com.example.autohub.presentation.utils.isValidEmail
import com.example.autohub.presentation.utils.isValidPhoneNumber
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
        _uiState.update { state ->
            state.copy(
                firstNameValue = value,
                isFirstNameError = !isNameValid(name = value)
            )
        }
    }

    fun updateLastNameValue(value: String) {
        _uiState.update { state ->
            state.copy(
                lastNameValue = value,
                isLastNameError = !isNameValid(name = value)
            )
        }
    }

    fun updateEmailValue(value: String) {
        _uiState.update { state ->
            state.copy(
                emailValue = value,
                isEmailError = !isValidEmail(email = value)
            )
        }
    }

    fun updatePasswordValue(value: String) {
        _uiState.update { state ->
            state.copy(
                passwordValue = value,
                isPasswordError = !isPasswordValid(password = value)
            )
        }
    }

    fun updateRepeatPasswordValue(value: String) {
        _uiState.update { state -> state.copy(repeatPasswordValue = value) }
    }

    fun updatePhoneValue(value: String) {
        _uiState.update { state ->
            state.copy(
                phoneValue = value,
                isPhoneError = !isValidPhoneNumber(number = value)
            )
        }
    }

    fun updateCityValue(value: String) {
        _uiState.update { state ->
            state.copy(
                cityValue = value,
                isCityError = !isValidCity(city = value)
            )
        }
    }

    fun registerAccount() {
        viewModelScope.launch {
            if (!isFullFieldsValid()) return@launch

            val newUser = formingNewAccount()
            val state = _uiState.value

            _uiState.update { state -> state.copy(loadingState = LoadingState.Loading) }

            when (
                val result = registerUserUseCase(
                    user = newUser.toUserDomain(),
                    email = state.emailValue,
                    password = state.passwordValue
                )
            ) {
                is FirebaseResult.Success -> {
                    _uiState.update { state -> state.copy(uiMessage = UiMessage(textResName = StringResNamePresentation.INFO_REGISTRATION_SUCCESS)) }
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
                                textResName = StringResNamePresentation.ERROR_REGISTRATION_FAILED,
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

    private fun isFullFieldsValid(): Boolean {
        val state = _uiState.value

        return when {
            !isAllFieldsFilled() -> {
                _uiState.update { it.copy(uiMessage = UiMessage(textResName = StringResNamePresentation.ERROR_FIELD_AND_OPTIONS_NOT_FILLED_IN)) }
                false
            }

            state.isFirstNameError || state.isLastNameError -> {
                _uiState.update { it.copy(uiMessage = UiMessage(textResName = StringResNamePresentation.ERROR_NAME_INCORRECT)) }
                false
            }

            state.isEmailError -> {
                _uiState.update { it.copy(uiMessage = UiMessage(textResName = StringResNamePresentation.ERROR_EMAIL_INCORRECT)) }
                false
            }

            state.isPhoneError -> {
                _uiState.update { it.copy(uiMessage = UiMessage(textResName = StringResNamePresentation.ERROR_PHONE_INCORRECT)) }
                false
            }

            state.isCityError -> {
                _uiState.update { it.copy(uiMessage = UiMessage(textResName = StringResNamePresentation.ERROR_CITY_INCORRECT)) }
                false
            }

            state.isPasswordError -> {
                _uiState.update { it.copy(uiMessage = UiMessage(textResName = StringResNamePresentation.ERROR_INCORRECT_PASSWORD)) }
                false
            }

            !isPasswordsMatch() -> {
                _uiState.update { it.copy(uiMessage = UiMessage(textResName = StringResNamePresentation.ERROR_PASSWORDS_DONT_MATCH)) }
                false
            }

            else -> true
        }
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

    private fun isAllFieldsFilled(): Boolean {
        val state = _uiState.value

        return state.firstNameValue.isNotBlank() && state.lastNameValue.isNotBlank() &&
                state.emailValue.isNotBlank() && state.phoneValue.isNotBlank() &&
                state.cityValue.isNotBlank() && state.passwordValue.isNotBlank() &&
                state.repeatPasswordValue.isNotBlank()
    }
}