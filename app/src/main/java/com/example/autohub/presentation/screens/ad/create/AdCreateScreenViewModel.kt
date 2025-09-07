package com.example.autohub.presentation.screens.ad.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autohub.domain.interfaces.usecase.CreateAdUseCase
import com.example.autohub.domain.interfaces.usecase.GetTokenFromDatabaseUseCase
import com.example.autohub.domain.interfaces.usecase.GetUserDataUseCase
import com.example.autohub.domain.model.ImageUploadData
import com.example.autohub.domain.model.result.DbResult
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.presentation.mapper.toCarAdDomain
import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.UiImage
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.model.options.BodyType
import com.example.autohub.presentation.model.options.ConditionType
import com.example.autohub.presentation.model.options.DriveType
import com.example.autohub.presentation.model.options.EngineType
import com.example.autohub.presentation.model.options.SteeringWheelSideType
import com.example.autohub.presentation.model.options.TransmissionType
import com.example.autohub.presentation.navigation.Navigator
import com.example.autohub.presentation.utils.isOnlyDigits
import com.example.autohub.presentation.utils.isOnlyLetters
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AdCreateScreenViewModel @Inject constructor(
    private val navigator: Navigator,
    private val createAd: CreateAdUseCase,
    private val getToken: GetTokenFromDatabaseUseCase,
    private val getUserData: GetUserDataUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdCreateScreenUiState())
    val uiState: StateFlow<AdCreateScreenUiState> = _uiState.asStateFlow()

    fun onBackButtonClick() {
        viewModelScope.launch {
            navigator.navigatePopBackStack()
        }
    }

    fun updateBrandValue(value: String) {
        if (value.isOnlyLetters()) {
            _uiState.update { state -> state.copy(brandValue = value) }
        }
    }

    fun updateModelValue(value: String) {
        if (value.isOnlyLetters()) {
            _uiState.update { state -> state.copy(modelValue = value) }
        }
    }

    fun updateColorValue(value: String) {
        if (value.isOnlyLetters()) {
            _uiState.update { state -> state.copy(colorValue = value) }
        }
    }

    fun updateRealiseYearValue(value: String) {
        if (value.isOnlyDigits()) {
            _uiState.update { state -> state.copy(realiseYearValue = value) }
        }
    }

    fun updateEngineCapacityValue(value: String) {
        _uiState.update { state -> state.copy(engineCapacityValue = value) }
    }

    fun updateMileageValue(value: String) {
        if (value.isOnlyDigits()) {
            _uiState.update { state -> state.copy(mileageValue = value) }
        }
    }

    fun updatePriceValue(value: String) {
        if (value.isOnlyDigits()) {
            _uiState.update { state -> state.copy(priceValue = value) }
        }
    }

    fun updateDescriptionValue(value: String) {
        _uiState.update { state -> state.copy(descriptionValue = value) }
    }

    fun updateBodyTypeValue(value: BodyType?) {
        _uiState.update { state -> state.copy(bodyTypeValue = value) }
    }

    fun updateEngineTypeValue(value: EngineType?) {
        _uiState.update { state -> state.copy(engineTypeValue = value) }
    }

    fun updateTransmissionValue(value: TransmissionType?) {
        _uiState.update { state -> state.copy(transmissionValue = value) }
    }

    fun updateDriveTypeValue(value: DriveType?) {
        _uiState.update { state -> state.copy(driveTypeValue = value) }
    }

    fun updateSteeringWheelSideValue(value: SteeringWheelSideType?) {
        _uiState.update { state -> state.copy(steeringWheelSideValue = value) }
    }

    fun updateConditionValue(value: ConditionType?) {
        _uiState.update { state -> state.copy(conditionValue = value) }
    }

    fun updateImageToShow(value: UiImage?) {
        _uiState.update { state ->
            state.copy(imageToShow = value)
        }
    }

    fun addImage(image: UiImage) {
        _uiState.update { state -> state.copy(images = state.images + image) }
    }

    fun onCreateAdClick() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isBrandValueError = state.brandValue.isBlank(),
                    isModelValueError = state.modelValue.isBlank(),
                    isColorValueError = state.colorValue.isBlank(),
                    isPriceValueError = state.priceValue.isBlank(),
                    isRealiseYearValueError = state.realiseYearValue.isBlank(),
                    isMileageValueError = state.mileageValue.isBlank(),
                    isEngineCapacityValue = state.engineCapacityValue.isBlank(),
                    isDriveTypeValueError = state.driveTypeValue == null,
                    isBodyTypeValueError = state.bodyTypeValue == null,
                    isEngineTypeValueError = state.engineTypeValue == null,
                    isTransmissionValueError = state.transmissionValue == null,
                    isSteeringWheelSideValueError = state.steeringWheelSideValue == null,
                    isConditionValueError = state.conditionValue == null
                )
            }

            val message = if (_uiState.value.images.isEmpty()) {
                "Necessary add images!"
            } else if (hasValidationErrors()) {
                "Necessary fill all fields and choose all options!"
            } else {
                formingCarAd()
                _uiState.value.carAd?.let {
                    createAd(
                        carAdInfo = it.toCarAdDomain(),
                        images = _uiState.value.images.mapIndexed { index, image ->
                            ImageUploadData(id = index, bytes = image.byteArray)
                        }
                    )
                }
                "Add is created successfully"
            }

            _uiState.update { state ->
                state.copy(message = message)
            }
        }
    }

    private suspend fun formingCarAd() {
        when (val tokenResult = getToken()) {
            is DbResult.Success -> {
                when (val userResult = getUserData(userUID = tokenResult.data)) {
                    is FirebaseResult.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                carAd = CarAd(
                                    brand = _uiState.value.brandValue,
                                    model = _uiState.value.modelValue,
                                    color = _uiState.value.colorValue,
                                    realiseYear = _uiState.value.realiseYearValue,
                                    body = _uiState.value.bodyTypeValue,
                                    typeEngine = _uiState.value.engineTypeValue,
                                    engineCapacity = _uiState.value.engineCapacityValue,
                                    transmission = _uiState.value.transmissionValue,
                                    drive = _uiState.value.driveTypeValue,
                                    steeringWheelSide = _uiState.value.steeringWheelSideValue,
                                    mileage = _uiState.value.mileageValue,
                                    condition = _uiState.value.conditionValue,
                                    price = _uiState.value.priceValue,
                                    description = _uiState.value.descriptionValue,
                                    userUID = tokenResult.data,
                                    city = userResult.data.city
                                )
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

            is DbResult.Error -> {
                _uiState.update { state ->
                    state.copy(
                        loadingState = LoadingState.Error(message = tokenResult.message)
                    )
                }
            }
        }
    }


    private fun hasValidationErrors(): Boolean {
        val state = _uiState.value

        return state.isBrandValueError || state.isModelValueError ||
                state.isColorValueError || state.isPriceValueError ||
                state.isRealiseYearValueError || state.isMileageValueError ||
                state.isDriveTypeValueError || state.isBodyTypeValueError ||
                state.isEngineCapacityValue || state.isEngineTypeValueError ||
                state.isTransmissionValueError || state.isSteeringWheelSideValueError ||
                state.isConditionValueError
    }
}