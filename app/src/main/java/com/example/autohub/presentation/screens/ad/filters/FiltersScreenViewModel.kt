package com.example.autohub.presentation.screens.ad.filters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autohub.presentation.model.SearchFilter
import com.example.autohub.presentation.model.options.BodyType
import com.example.autohub.presentation.model.options.CarOption
import com.example.autohub.presentation.model.options.ConditionType
import com.example.autohub.presentation.model.options.DriveType
import com.example.autohub.presentation.model.options.EngineType
import com.example.autohub.presentation.model.options.SteeringWheelSideType
import com.example.autohub.presentation.model.options.TransmissionType
import com.example.autohub.presentation.navigation.Navigator
import com.example.autohub.presentation.navigation.model.graphs.destinations.AdGraph
import com.example.autohub.presentation.utils.isOnlyDigits
import com.example.autohub.presentation.utils.isOnlyLetters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FiltersScreenViewModel @Inject constructor(
    private val navigator: Navigator,
) : ViewModel() {

    private val _uiState = MutableStateFlow(FiltersScreenUiState())
    val uiState: StateFlow<FiltersScreenUiState> = _uiState.asStateFlow()

    fun onBackButtonClick() {
        viewModelScope.launch {
            navigator.navigatePopBackStack()
        }
    }

    fun onConfirmClick() {
        viewModelScope.launch {
            val state = _uiState.value
            val createdFilters = buildList {
                addIfNotBlank(name = "brand", value = state.brandValue)
                addIfNotBlank(name = "model", value = state.modelValue)
                addIfNotBlank(name = "color", value = state.colorValue)
                addIfNotBlank(name = "realiseYear", value = state.realiseYearValue)
                addIfNotBlank(name = "engineCapacity", value = state.engineCapacityValue)
                addIfNotBlank(name = "mileage", value = state.mileageValue)
                addIfNotBlank(name = "price", value = state.priceValue)
                addIfNotBlank(name = "description", value = state.descriptionValue)
                addIfNotBlank(name = "city", value = state.cityValue)
                addIfNotNull(name = "bodyType", value = state.bodyTypeValue)
                addIfNotNull(name = "engineType", value = state.engineTypeValue)
                addIfNotNull(name = "transmission", value = state.transmissionValue)
                addIfNotNull(name = "driveType", value = state.driveTypeValue)
                addIfNotNull(name = "steeringWheelSide", value = state.steeringWheelSideValue)
                addIfNotNull(name = "condition", value = state.conditionValue)
            }

            navigator.navigate(
                destination = AdGraph.AdsMainScreen
            )
        }
    }

    fun onClearFiltersClick() {
        _uiState.value = FiltersScreenUiState()
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

    fun updateCityValue(value: String) {
        _uiState.update { state -> state.copy(cityValue = value) }
    }

    private fun MutableList<SearchFilter>.addIfNotBlank(name: String, value: String) {
        if (value.isNotBlank()) {
            add(SearchFilter(name, value))
        }
    }

    private fun MutableList<SearchFilter>.addIfNotNull(name: String, value: CarOption?) {
        value?.let { add(SearchFilter(name, it.tag)) }
    }
}