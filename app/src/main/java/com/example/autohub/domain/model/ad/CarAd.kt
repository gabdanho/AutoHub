package com.example.autohub.domain.model.ad

/**
 * Модель объявления автомобиля.
 *
 * @property userId Идентификатор пользователя, который создал объявление.
 * @property adId Идентификатор объявления.
 * @property city Город размещения объявления.
 * @property brand Марка автомобиля.
 * @property model Модель автомобиля.
 * @property realiseYear Год выпуска.
 * @property price Цена автомобиля.
 * @property body Тип кузова.
 * @property typeEngine Тип двигателя.
 * @property transmission Коробка передач.
 * @property drive Привод.
 * @property description Описание автомобиля.
 * @property condition Состояние автомобиля.
 * @property engineCapacity Объём двигателя.
 * @property steeringWheelSide Расположение руля.
 * @property mileage Пробег автомобиля.
 * @property color Цвет автомобиля.
 * @property timeMillis Время создания объявления в миллисекундах.
 * @property imagesUrl Список URL изображений автомобиля.
 */
data class CarAd(
    val userId: String = "",
    val adId: String = "",
    val city: String = "",
    val brand: String = "",
    val model: String = "",
    val realiseYear: String = "",
    val price: String = "",
    val body: String = "",
    val typeEngine: String = "",
    val transmission: String = "",
    val drive: String = "",
    val description: String = "",
    val condition: String = "",
    val engineCapacity: String = "",
    val steeringWheelSide: String = "",
    val mileage: String = "",
    val color: String = "",
    val timeMillis: Long = 0L,
    val imagesUrl: List<String> = emptyList(),
)