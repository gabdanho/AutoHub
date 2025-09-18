package com.example.autohub.data.firebase.model.ad

/**
 * Объявление автомобиля.
 *
 * @param userId ID пользователя
 * @param adId ID объявления
 * @param city Город
 * @param brand Марка
 * @param model Модель
 * @param realiseYear Год выпуска
 * @param price Цена
 * @param body Тип кузова
 * @param typeEngine Тип двигателя
 * @param transmission Коробка передач
 * @param drive Привод
 * @param description Описание
 * @param condition Состояние
 * @param engineCapacity Объем двигателя
 * @param steeringWheelSide Руль (левый/правый)
 * @param mileage Пробег
 * @param color Цвет
 * @param timeMillis Время создания
 * @param imagesUrl Ссылки на изображения
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
    val imagesUrl: List<String> = listOf()
)