package com.example.autohub.presentation.screens.ad.create

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.model.options.BodyType
import com.example.autohub.presentation.model.options.ConditionType
import com.example.autohub.presentation.model.options.DriveType
import com.example.autohub.presentation.model.options.EngineType
import com.example.autohub.presentation.model.options.SteeringWheelSideType
import com.example.autohub.presentation.model.options.TransmissionType

data class AdCreateScreenUiState(
    val brandValue: String = "",
    val modelValue: String = "",
    val colorValue: String = "",
    val realiseYearValue: String = "",
    val engineCapacityValue: String = "",
    val mileageValue: String = "",
    val priceValue: String = "",
    val descriptionValue: String = "",

    val bodyTypeValue: BodyType? = null,
    val engineTypeValue: EngineType? = null,
    val transmissionValue: TransmissionType? = null,
    val driveTypeValue: DriveType? = null,
    val steeringWheelSideValue: SteeringWheelSideType? = null,
    val conditionValue: ConditionType? = null,

    val imagesValue: List<String> = emptyList(),
    val imageToShow: String? = null,

    val isBrandValueError: Boolean = false,
    val isModelValueError: Boolean = false,
    val isColorValueError: Boolean = false,
    val isRealiseYearValueError: Boolean = false,
    val isEngineCapacityValue: Boolean = false,
    val isMileageValueError: Boolean = false,
    val isPriceValueError: Boolean = false,

    val isBodyTypeValueError: Boolean = false,
    val isEngineTypeValueError: Boolean = false,
    val isTransmissionValueError: Boolean = false,
    val isDriveTypeValueError: Boolean = false,
    val isSteeringWheelSideValueError: Boolean = false,
    val isConditionValueError: Boolean = false,
    val isDescriptionValueError: Boolean = false,

    val message: String? = null,
    val loadingState: LoadingState? = null
)
