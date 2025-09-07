package com.example.autohub.presentation.screens.ad.filters

import com.example.autohub.presentation.model.options.BodyType
import com.example.autohub.presentation.model.options.ConditionType
import com.example.autohub.presentation.model.options.DriveType
import com.example.autohub.presentation.model.options.EngineType
import com.example.autohub.presentation.model.options.SteeringWheelSideType
import com.example.autohub.presentation.model.options.TransmissionType

data class FiltersScreenUiState(
    val brandValue: String = "",
    val modelValue: String = "",
    val colorValue: String = "",
    val realiseYearValue: String = "",
    val engineCapacityValue: String = "",
    val mileageValue: String = "",
    val priceValue: String = "",
    val descriptionValue: String = "",
    val cityValue: String = "",

    val bodyTypeValue: BodyType? = null,
    val engineTypeValue: EngineType? = null,
    val transmissionValue: TransmissionType? = null,
    val driveTypeValue: DriveType? = null,
    val steeringWheelSideValue: SteeringWheelSideType? = null,
    val conditionValue: ConditionType? = null
)
