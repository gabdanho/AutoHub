package com.example.autohub.presentation.model.ad

import com.example.autohub.presentation.model.options.BodyType
import com.example.autohub.presentation.model.options.ConditionType
import com.example.autohub.presentation.model.options.DriveType
import com.example.autohub.presentation.model.options.EngineType
import com.example.autohub.presentation.model.options.SteeringWheelSideType
import com.example.autohub.presentation.model.options.TransmissionType
import kotlinx.serialization.Serializable

/**
 * Модель объявления о продаже автомобиля для Presentation слоя.
 *
 * @property userId ID пользователя, который создал объявление
 * @property dateAdPublished Дата публикации объявления
 * @property brand Марка автомобиля
 * @property model Модель автомобиля
 * @property realiseYear Год выпуска
 * @property price Цена автомобиля
 * @property body Тип кузова ([BodyType])
 * @property typeEngine Тип двигателя ([EngineType])
 * @property transmission Тип трансмиссии ([TransmissionType])
 * @property drive Тип привода ([DriveType])
 * @property description Описание автомобиля
 * @property condition Состояние автомобиля ([ConditionType])
 * @property engineCapacity Объем двигателя
 * @property steeringWheelSide Сторона руля ([SteeringWheelSideType])
 * @property mileage Пробег
 * @property color Цвет автомобиля
 * @property imagesUrl Список URL изображений
 * @property timeMillis Время публикации в миллисекундах
 * @property city Город
 */
@Serializable
data class CarAd(
    val userId: String = "",
    val dateAdPublished: String = "",
    val brand: String = "",
    val model: String = "",
    val realiseYear: String = "",
    val price: String = "",
    val body: BodyType? = null,
    val typeEngine: EngineType? = null,
    val transmission: TransmissionType? = null,
    val drive: DriveType? = null,
    val description: String = "",
    val condition: ConditionType? = null,
    val engineCapacity: String = "",
    val steeringWheelSide: SteeringWheelSideType? = null,
    val mileage: String = "",
    val color: String = "",
    val imagesUrl: List<String> = listOf(),
    val timeMillis: Long = 0L,
    val city: String = ""
)