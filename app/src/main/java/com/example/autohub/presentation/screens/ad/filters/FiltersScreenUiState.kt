package com.example.autohub.presentation.screens.ad.filters

import com.example.autohub.presentation.model.options.BodyType
import com.example.autohub.presentation.model.options.ConditionType
import com.example.autohub.presentation.model.options.DriveType
import com.example.autohub.presentation.model.options.EngineType
import com.example.autohub.presentation.model.options.SteeringWheelSideType
import com.example.autohub.presentation.model.options.TransmissionType

/**
 * UI-состояние экрана фильтров [FiltersScreen].
 *
 * Хранит текущие значения всех полей фильтров.
 *
 * @property brandValue Значение фильтра по марке автомобиля.
 * @property modelValue Значение фильтра по модели автомобиля.
 * @property colorValue Значение фильтра по цвету автомобиля.
 * @property realiseYearValue Значение фильтра по году выпуска автомобиля.
 * @property engineCapacityValue Значение фильтра по объему двигателя.
 * @property mileageValue Значение фильтра по пробегу автомобиля.
 * @property priceValue Значение фильтра по цене автомобиля.
 * @property descriptionValue Значение фильтра по описанию объявления.
 * @property cityValue Значение фильтра по городу.
 * @property bodyTypeValue Значение фильтра по типу кузова [BodyType].
 * @property engineTypeValue Значение фильтра по типу двигателя [EngineType].
 * @property transmissionValue Значение фильтра по типу коробки передач [TransmissionType].
 * @property driveTypeValue Значение фильтра по типу привода [DriveType].
 * @property steeringWheelSideValue Значение фильтра по стороне руля [SteeringWheelSideType].
 * @property conditionValue Значение фильтра по состоянию автомобиля [ConditionType].
 */
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
    val conditionValue: ConditionType? = null,
)
