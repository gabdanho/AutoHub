package com.example.autohub.presentation.screens.ad.filters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autohub.presentation.constants.CarOptionsNamesConstants.FIELD_BODY_TYPE
import com.example.autohub.presentation.constants.CarOptionsNamesConstants.FIELD_BRAND
import com.example.autohub.presentation.constants.CarOptionsNamesConstants.FIELD_CITY
import com.example.autohub.presentation.constants.CarOptionsNamesConstants.FIELD_COLOR
import com.example.autohub.presentation.constants.CarOptionsNamesConstants.FIELD_CONDITION
import com.example.autohub.presentation.constants.CarOptionsNamesConstants.FIELD_DESCRIPTION
import com.example.autohub.presentation.constants.CarOptionsNamesConstants.FIELD_DRIVE_TYPE
import com.example.autohub.presentation.constants.CarOptionsNamesConstants.FIELD_ENGINE_CAPACITY
import com.example.autohub.presentation.constants.CarOptionsNamesConstants.FIELD_ENGINE_TYPE
import com.example.autohub.presentation.constants.CarOptionsNamesConstants.FIELD_MILEAGE
import com.example.autohub.presentation.constants.CarOptionsNamesConstants.FIELD_MODEL
import com.example.autohub.presentation.constants.CarOptionsNamesConstants.FIELD_PRICE
import com.example.autohub.presentation.constants.CarOptionsNamesConstants.FIELD_REALISE_YEAR
import com.example.autohub.presentation.constants.CarOptionsNamesConstants.FIELD_STEERING_WHEEL_SIDE
import com.example.autohub.presentation.constants.CarOptionsNamesConstants.FIELD_TRANSMISSION
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
import com.example.autohub.presentation.navigation.model.nav_type.SearchFiltersNav
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

    fun initFilters(filters: List<SearchFilter>) {
        _uiState.update { state ->
            state.copy(
                brandValue = findInFilters<String>(filterName = FIELD_BRAND, filters = filters)
                    ?: state.brandValue,
                modelValue = findInFilters<String>(filterName = FIELD_MODEL, filters = filters)
                    ?: state.modelValue,
                colorValue = findInFilters<String>(filterName = FIELD_COLOR, filters = filters)
                    ?: state.colorValue,
                realiseYearValue = findInFilters<String>(
                    filterName = FIELD_REALISE_YEAR,
                    filters = filters
                ) ?: state.realiseYearValue,
                engineCapacityValue = findInFilters<String>(
                    filterName = FIELD_ENGINE_CAPACITY,
                    filters = filters
                ) ?: state.engineCapacityValue,
                mileageValue = findInFilters<String>(filterName = FIELD_MILEAGE, filters = filters)
                    ?: state.mileageValue,
                priceValue = findInFilters<String>(filterName = FIELD_PRICE, filters = filters)
                    ?: state.priceValue,
                descriptionValue = findInFilters<String>(
                    filterName = FIELD_DESCRIPTION,
                    filters = filters
                ) ?: state.descriptionValue,
                cityValue = findInFilters<String>(filterName = FIELD_CITY, filters = filters)
                    ?: state.cityValue,
                bodyTypeValue = BodyType.fromTag(
                    value = findInFilters<String>(
                        filterName = FIELD_BODY_TYPE,
                        filters = filters
                    )
                ),
                engineTypeValue = EngineType.fromTag(
                    value = findInFilters<String>(
                        filterName = FIELD_ENGINE_TYPE,
                        filters = filters
                    )
                ),
                transmissionValue = TransmissionType.fromTag(
                    value = findInFilters<String>(
                        filterName = FIELD_TRANSMISSION,
                        filters = filters
                    )
                ),
                driveTypeValue = DriveType.fromTag(
                    value = findInFilters<String>(
                        filterName = FIELD_DRIVE_TYPE,
                        filters = filters
                    )
                ),
                steeringWheelSideValue = SteeringWheelSideType.fromTag(
                    value = findInFilters<String>(
                        filterName = FIELD_STEERING_WHEEL_SIDE,
                        filters = filters
                    )
                ),
                conditionValue = ConditionType.fromTag(
                    value = findInFilters<String>(
                        filterName = FIELD_CONDITION,
                        filters = filters
                    )
                )
            )
        }
    }

    fun onBackButtonClick() {
        viewModelScope.launch {
            navigator.navigatePopBackStack()
        }
    }

    fun onConfirmClick() {
        viewModelScope.launch {
            val state = _uiState.value
            val createdFilters = buildList {
                addIfNotBlank(name = FIELD_BRAND, value = state.brandValue)
                addIfNotBlank(name = FIELD_MODEL, value = state.modelValue)
                addIfNotBlank(name = FIELD_COLOR, value = state.colorValue)
                addIfNotBlank(name = FIELD_REALISE_YEAR, value = state.realiseYearValue)
                addIfNotBlank(name = FIELD_ENGINE_CAPACITY, value = state.engineCapacityValue)
                addIfNotBlank(name = FIELD_MILEAGE, value = state.mileageValue)
                addIfNotBlank(name = FIELD_PRICE, value = state.priceValue)
                addIfNotBlank(name = FIELD_DESCRIPTION, value = state.descriptionValue)
                addIfNotBlank(name = FIELD_CITY, value = state.cityValue)
                addIfNotNull(name = FIELD_BODY_TYPE, value = state.bodyTypeValue)
                addIfNotNull(name = FIELD_ENGINE_TYPE, value = state.engineTypeValue)
                addIfNotNull(name = FIELD_TRANSMISSION, value = state.transmissionValue)
                addIfNotNull(name = FIELD_DRIVE_TYPE, value = state.driveTypeValue)
                addIfNotNull(name = FIELD_STEERING_WHEEL_SIDE, value = state.steeringWheelSideValue)
                addIfNotNull(name = FIELD_CONDITION, value = state.conditionValue)
            }

            navigator.navigate(
                destination = AdGraph.AdsMainScreen(searchFilters = SearchFiltersNav(filters = createdFilters))
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

    private inline fun <reified T> findInFilters(
        filterName: String,
        filters: List<SearchFilter>,
    ): T? {
        return filters.find { it.name == filterName }?.value as T?
    }
}