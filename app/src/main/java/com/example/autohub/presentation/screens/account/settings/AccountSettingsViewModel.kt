package com.example.autohub.presentation.screens.account.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autohub.domain.interfaces.usecase.ChangePasswordUseCase
import com.example.autohub.domain.interfaces.usecase.GetLocalUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.GetUserDataUseCase
import com.example.autohub.domain.interfaces.usecase.UpdateCityUseCase
import com.example.autohub.domain.interfaces.usecase.UpdateFirstNameUseCase
import com.example.autohub.domain.interfaces.usecase.UpdateLastNameUseCase
import com.example.autohub.domain.interfaces.usecase.UploadUserProfileImageToFirebaseUseCase
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.presentation.mapper.toUserPresentation
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.navigation.Navigator
import com.example.autohub.presentation.navigation.model.graphs.destinations.AccountGraph
import com.example.autohub.presentation.utils.isOnlyLetters
import com.example.autohub.presentation.utils.isPasswordValid
import com.example.autohub.presentation.utils.isValidCity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountSettingsViewModel @Inject constructor(
    private val navigator: Navigator,
    private val uploadProfileImage: UploadUserProfileImageToFirebaseUseCase,
    private val getUserData: GetUserDataUseCase,
    private val getToken: GetLocalUserIdUseCase,
    private val updateFirstNameName: UpdateFirstNameUseCase,
    private val updateLastNameName: UpdateLastNameUseCase,
    private val updateCity: UpdateCityUseCase,
    private val changePassword: ChangePasswordUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountSettingsUiState())
    val uiState: StateFlow<AccountSettingsUiState> = _uiState.asStateFlow()

    init {
        getUser()
    }

    fun updateIsShowChangePasswordDialog(value: Boolean) {
        _uiState.update { state -> state.copy(isShowChangePasswordDialog = value) }
    }

    fun updateFirstNameValue(value: String) {
        _uiState.update { state ->
            state.copy(
                firstNameValue = value,
                isFirstNameValueError = !value.isOnlyLetters()
            )
        }
        isNamesButtonAvailable()
    }

    fun updateLastNameValue(value: String) {
        _uiState.update { state ->
            state.copy(
                lastNameValue = value,
                isLastNameValueError = !value.isOnlyLetters()
            )
        }
        isNamesButtonAvailable()
    }

    fun updateCityValue(value: String) {
        _uiState.update { state ->
            state.copy(
                cityValue = value,
                isCityValueError = isValidCity(value)
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

    fun onBackButtonClick() {
        viewModelScope.launch {
            navigator.navigate(
                destination = AccountGraph.AuthUserAccountScreen
            )
        }
    }

    fun uploadUserProfileImageToFirebase(imageRef: ImageUploadData, uriString: String) {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(loadingState = LoadingState.Loading) }

            if (imageRef.bytes != null) {
                when (val result = uploadProfileImage(imageRef = imageRef)) {
                    is FirebaseResult.Success -> {
                        _uiState.update { state -> state.copy(user = state.user.copy(image = uriString)) }
                    }

                    is FirebaseResult.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                loadingState = LoadingState.Error(
                                    message = result.message
                                )
                            )
                        }
                    }
                }
            } else _uiState.update { state ->
                state.copy(
                    loadingState = LoadingState.Error(message = "Can't upload image.")
                )
            }
        }
    }

    fun acceptNamesChanges() {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(loadingState = LoadingState.Loading) }

            val firstName = _uiState.value.firstNameValue
            val lastName = _uiState.value.lastNameValue
            val isFirstNameCorrect = !_uiState.value.isFirstNameValueError
            val isLastNameCorrect = !_uiState.value.isLastNameValueError

            if (isFirstNameCorrect && firstName.isNotBlank()) {
                when (val result = updateFirstNameName(firstName = firstName)) {
                    is FirebaseResult.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                user = state.user.copy(firstName = firstName),
                                firstNameValue = ""
                            )
                        }
                    }

                    is FirebaseResult.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                loadingState = LoadingState.Error(
                                    message = result.message
                                )
                            )
                        }
                    }
                }
            }
            if (isLastNameCorrect && lastName.isNotBlank()) {
                when (val result = updateLastNameName(lastName = lastName)) {
                    is FirebaseResult.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                user = state.user.copy(lastName = lastName),
                                lastNameValue = ""
                            )
                        }
                    }

                    is FirebaseResult.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                loadingState = LoadingState.Error(
                                    message = result.message
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun acceptCityChange() {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(loadingState = LoadingState.Loading) }

            val city = _uiState.value.cityValue

            when (val result = updateCity(city = city)) {
                is FirebaseResult.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            user = state.user.copy(city = city),
                            cityValue = ""
                        )
                    }
                }

                is FirebaseResult.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = LoadingState.Error(
                                message = result.message
                            )
                        )
                    }
                }
            }
        }
    }

    fun setNewPassword() {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(loadingState = LoadingState.Loading) }

            val password = _uiState.value.passwordValue

            val message: String = if (password.isEmpty()) {
                "Password is empty"
            } else if (_uiState.value.isPasswordError) {
                "Incorrect password"
            } else {
                when (val result = changePassword(newPassword = password)) {
                    is FirebaseResult.Success -> {
                        _uiState.update { state -> state.copy(passwordValue = "") }
                        "Password is changed"
                    }

                    is FirebaseResult.Error -> {
                        result.message
                    }
                }
            }

            _uiState.update { state -> state.copy(passwordMessage = message) }
        }
    }

    private fun isNamesButtonAvailable() {
        val firstName = _uiState.value.firstNameValue
        val lastName = _uiState.value.lastNameValue
        val isFirstNameCorrect = !_uiState.value.isFirstNameValueError
        val isLastNameCorrect = !_uiState.value.isLastNameValueError

        val isAvailable =
            (isFirstNameCorrect && isLastNameCorrect) || (firstName.isBlank() && isLastNameCorrect) || (lastName.isBlank() && isFirstNameCorrect)

        _uiState.update { state -> state.copy(isNamesButtonEnabled = isAvailable) }
    }

    private fun getUser() {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(loadingState = LoadingState.Loading) }

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