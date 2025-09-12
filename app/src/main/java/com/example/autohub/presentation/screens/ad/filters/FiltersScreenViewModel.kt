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
                brandValue = findInFilters<String>(filterName = "brand", filters = filters)
                    ?: state.brandValue,
                modelValue = findInFilters<String>(filterName = "model", filters = filters)
                    ?: state.modelValue,
                colorValue = findInFilters<String>(filterName = "color", filters = filters)
                    ?: state.colorValue,
                realiseYearValue = findInFilters<String>(
                    filterName = "realiseYear",
                    filters = filters
                ) ?: state.realiseYearValue,
                engineCapacityValue = findInFilters<String>(
                    filterName = "engineCapacity",
                    filters = filters
                ) ?: state.engineCapacityValue,
                mileageValue = findInFilters<String>(filterName = "mileage", filters = filters)
                    ?: state.mileageValue,
                priceValue = findInFilters<String>(filterName = "price", filters = filters)
                    ?: state.priceValue,
                descriptionValue = findInFilters<String>(
                    filterName = "description",
                    filters = filters
                ) ?: state.descriptionValue,
                cityValue = findInFilters<String>(filterName = "city", filters = filters)
                    ?: state.cityValue,
                bodyTypeValue = BodyType.fromTag(
                    value = findInFilters<String>(
                        filterName = "bodyType",
                        filters = filters
                    )
                ),
                engineTypeValue = EngineType.fromTag(
                    value = findInFilters<String>(
                        filterName = "engineType",
                        filters = filters
                    )
                ),
                transmissionValue = TransmissionType.fromTag(
                    value = findInFilters<String>(
                        filterName = "transmission",
                        filters = filters
                    )
                ),
                driveTypeValue = DriveType.fromTag(
                    value = findInFilters<String>(
                        filterName = "driveType",
                        filters = filters
                    )
                ),
                steeringWheelSideValue = SteeringWheelSideType.fromTag(
                    value = findInFilters<String>(
                        filterName = "steeringWheelSide",
                        filters = filters
                    )
                ),
                conditionValue = ConditionType.fromTag(
                    value = findInFilters<String>(
                        filterName = "condition",
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