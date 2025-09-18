package com.example.autohub.presentation.mapper

import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.presentation.model.options.BodyType
import com.example.autohub.presentation.model.options.ConditionType
import com.example.autohub.presentation.model.options.DriveType
import com.example.autohub.presentation.model.options.EngineType
import com.example.autohub.presentation.model.options.SteeringWheelSideType
import com.example.autohub.presentation.model.options.TransmissionType
import com.example.autohub.domain.model.ad.CarAd as CarAdDomain

/**
 * Конвертация [CarAd] в [CarAdDomain].
 *
 * @receiver Presentation-модель объявления
 * @return Domain-модель объявления
 */
fun CarAd.toCarAdDomain(): CarAdDomain {
    return CarAdDomain(
        userId = userId,
        brand = brand,
        model = model,
        realiseYear = realiseYear,
        price = price,
        body = body?.tag ?: "",
        typeEngine = typeEngine?.tag ?: "",
        transmission = transmission?.tag ?: "",
        drive = drive?.tag ?: "",
        description = description,
        condition = condition?.tag ?: "",
        engineCapacity = engineCapacity,
        steeringWheelSide = steeringWheelSide?.tag ?: "",
        mileage = mileage,
        color = color,
        imagesUrl = imagesUrl,
        timeMillis = timeMillis,
        city = city,
    )
}

/**
 * Конвертация [CarAdDomain] в [CarAd].
 *
 * @receiver Domain-модель объявления
 * @return Presentation-модель объявления
 */
fun CarAdDomain.toCarAdPresentation(): CarAd {
    return CarAd(
        userId = userId,
        brand = brand,
        model = model,
        realiseYear = realiseYear,
        price = price,
        body = BodyType.fromTag(value = body),
        typeEngine = EngineType.fromTag(value = typeEngine),
        transmission = TransmissionType.fromTag(value = transmission),
        drive = DriveType.fromTag(value = drive),
        description = description,
        condition = ConditionType.fromTag(value = condition),
        engineCapacity = engineCapacity,
        steeringWheelSide = SteeringWheelSideType.fromTag(value = steeringWheelSide),
        mileage = mileage,
        color = color,
        imagesUrl = imagesUrl,
        timeMillis = timeMillis,
        city = city,
    )
}

/**
 * Конвертация списка [CarAdDomain] в список [CarAd] с форматированием даты публикации.
 *
 * @param ads Список объявлений
 * @param millisToDate Функция форматирования времени в дату
 * @return Список Presentation-моделей объявлений
 */
fun mapListCarAdDomainToPresentation(ads: List<CarAdDomain>, millisToDate: (Long) -> String): List<CarAd> {
    return ads.map { carAd ->
        carAd.toCarAdPresentation()
            .copy(dateAdPublished = millisToDate(carAd.timeMillis))
    }
}
