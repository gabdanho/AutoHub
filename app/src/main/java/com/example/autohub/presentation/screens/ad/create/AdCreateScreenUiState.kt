package com.example.autohub.presentation.screens.ad.create

import com.example.autohub.presentation.model.LoadingState
import com.example.autohub.presentation.model.UiImage
import com.example.autohub.presentation.model.UiMessage
import com.example.autohub.presentation.model.options.BodyType
import com.example.autohub.presentation.model.options.ConditionType
import com.example.autohub.presentation.model.options.DriveType
import com.example.autohub.presentation.model.options.EngineType
import com.example.autohub.presentation.model.options.SteeringWheelSideType
import com.example.autohub.presentation.model.options.TransmissionType

/**
 * UI состояние экрана создания объявления.
 *
 * @property brandValue Марка автомобиля.
 * @property modelValue Модель автомобиля.
 * @property colorValue Цвет автомобиля.
 * @property realiseYearValue Год выпуска.
 * @property engineCapacityValue Объем двигателя в виде строки.
 * @property mileageValue Пробег автомобиля.
 * @property priceValue Цена автомобиля.
 * @property descriptionValue Описание автомобиля.
 * @property bodyTypeValue Выбранный тип кузова.
 * @property engineTypeValue Выбранный тип двигателя.
 * @property transmissionValue Выбранный тип трансмиссии.
 * @property driveTypeValue Выбранный тип привода.
 * @property steeringWheelSideValue Сторона руля.
 * @property conditionValue Состояние автомобиля.
 * @property images Список загруженных изображений.
 * @property imageIdToShow Индекс текущего изображения для просмотра в пейджере.
 * @property isShowImagePager Показывать ли пейджер изображений.
 * @property isBrandValueError Ошибка в поле марки.
 * @property isModelValueError Ошибка в поле модели.
 * @property isColorValueError Ошибка в поле цвета.
 * @property isRealiseYearValueError Ошибка в поле года выпуска.
 * @property isEngineCapacityValue Ошибка в поле объема двигателя.
 * @property isMileageValueError Ошибка в поле пробега.
 * @property isPriceValueError Ошибка в поле цены.
 * @property isBodyTypeValueError Ошибка выбора типа кузова.
 * @property isEngineTypeValueError Ошибка выбора типа двигателя.
 * @property isTransmissionValueError Ошибка выбора трансмиссии.
 * @property isDriveTypeValueError Ошибка выбора типа привода.
 * @property isSteeringWheelSideValueError Ошибка выбора стороны руля.
 * @property isConditionValueError Ошибка выбора состояния.
 * @property isDescriptionValueError Ошибка в поле описания.
 * @property loadingState Состояние загрузки (Loading, Success, Error).
 * @property uiMessage Сообщение для отображения пользователю.
 */
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

    val images: List<UiImage> = emptyList(),
    val imageIdToShow: Int = 0,
    val isShowImagePager: Boolean = false,

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

    val loadingState: LoadingState? = null,
    val uiMessage: UiMessage = UiMessage(),
)
