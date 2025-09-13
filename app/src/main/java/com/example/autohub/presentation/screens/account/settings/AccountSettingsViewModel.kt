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
import com.example.autohub.presentation.mapper.toStringResNamePresentation
import com.example.autohub.presentation.mapper.toUserPresentation
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.StringResNamePresentation
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
    private val uploadUserProfileImageToFirebaseUseCase: UploadUserProfileImageToFirebaseUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getLocalUserIdUseCase: GetLocalUserIdUseCase,
    private val updateFirstNameUseCase: UpdateFirstNameUseCase,
    private val updateLastNameUseCase: UpdateLastNameUseCase,
    private val updateCityUseCase: UpdateCityUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
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
                isCityValueError = !isValidCity(value)
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

            when (val result = uploadUserProfileImageToFirebaseUseCase(imageRef = imageRef)) {
                is FirebaseResult.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            user = state.user.copy(image = uriString),
                            loadingState = LoadingState.Success,
                            message = StringResNamePresentation.INFO_UPDATE_USER_FIELDS_SUCCESS
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
                            loadingState = LoadingState.Error(
                                message = result.message
                            ),
                            message = StringResNamePresentation.ERROR_UPDATE_USER_FIELDS,
                            messageDetails = result.message
                        )
                    }
                }
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
                when (val result = updateFirstNameUseCase(firstName = firstName)) {
                    is FirebaseResult.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                user = state.user.copy(firstName = firstName),
                                loadingState = LoadingState.Success,
                                firstNameValue = "",
                                isNamesButtonEnabled = false,
                                message = StringResNamePresentation.INFO_UPDATE_USER_FIELDS_SUCCESS
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
                                loadingState = LoadingState.Error(
                                    message = result.message
                                ),
                                message = StringResNamePresentation.ERROR_UPDATE_USER_FIELDS,
                                messageDetails = result.message
                            )
                        }
                    }
                }
            }
            if (isLastNameCorrect && lastName.isNotBlank()) {
                when (val result = updateLastNameUseCase(lastName = lastName)) {
                    is FirebaseResult.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                user = state.user.copy(lastName = lastName),
                                loadingState = LoadingState.Success,
                                lastNameValue = "",
                                isNamesButtonEnabled = false,
                                message = StringResNamePresentation.INFO_UPDATE_USER_FIELDS_SUCCESS
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
                                loadingState = LoadingState.Error(
                                    message = result.message
                                ),
                                message = StringResNamePresentation.ERROR_UPDATE_USER_FIELDS,
                                messageDetails = result.message
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

            when (val result = updateCityUseCase(city = city)) {
                is FirebaseResult.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            user = state.user.copy(city = city),
                            loadingState = LoadingState.Success,
                            cityValue = "",
                            isCityValueError = true,
                            message = StringResNamePresentation.INFO_UPDATE_USER_FIELDS_SUCCESS
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
                            loadingState = LoadingState.Error(
                                message = result.message
                            ),
                            message = StringResNamePresentation.ERROR_UPDATE_USER_FIELDS,
                            messageDetails = result.message
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

            val message = if (password.isEmpty()) {
                StringResNamePresentation.ERROR_EMPTY_PASSWORD
            } else if (_uiState.value.isPasswordError) {
                StringResNamePresentation.ERROR_INCORRECT_PASSWORD
            } else {
                when (val result = changePasswordUseCase(newPassword = password)) {
                    is FirebaseResult.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                passwordValue = "",
                                loadingState = LoadingState.Success
                            )
                        }
                        StringResNamePresentation.INFO_PASSWORD_IS_CHANGED
                    }

                    is FirebaseResult.Error.TimeoutError -> {
                        StringResNamePresentation.ERROR_TIMEOUT_ERROR
                    }

                    is FirebaseResult.Error.HandledError -> {
                        result.tag.toStringResNamePresentation()
                    }

                    is FirebaseResult.Error -> {
                        _uiState.update { state -> state.copy(messageDetails = result.message) }
                        StringResNamePresentation.ERROR_FAILED_CHANGE_PASSWORD
                    }
                }
            }

            _uiState.update { state -> state.copy(message = message) }
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

            val uid = getLocalUserIdUseCase()

            uid?.let {
                when (val userResult = getUserDataUseCase(userUID = uid)) {
                    is FirebaseResult.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                user = userResult.data.toUserPresentation().copy(uid = uid),
                                loadingState = LoadingState.Success
                            )
                        }
                    }

                    is FirebaseResult.Error.TimeoutError -> {
                        _uiState.update { state ->
                            state.copy(
                                message = StringResNamePresentation.ERROR_TIMEOUT_ERROR,
                                loadingState = LoadingState.Error(message = userResult.message)
                            )
                        }
                    }

                    is FirebaseResult.Error.HandledError -> {
                        _uiState.update { state ->
                            state.copy(
                                message = userResult.tag.toStringResNamePresentation(),
                                loadingState = LoadingState.Error(message = userResult.message)
                            )
                        }
                    }

                    is FirebaseResult.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                loadingState = LoadingState.Error(message = userResult.message),
                                message = StringResNamePresentation.ERROR_UPDATE_USER_FIELDS,
                                messageDetails = userResult.message
                            )
                        }
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
}