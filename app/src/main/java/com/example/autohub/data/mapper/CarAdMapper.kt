package com.example.autohub.data.mapper

import com.example.autohub.data.firebase.model.ad.CarAd
import com.example.autohub.domain.model.ad.CarAd as CarAdDomain

/**
 * Конвертация [CarAdDomain] в [CarAd].
 *
 * @receiver Domain-модель объявления
 * @return Data-модель объявления
 */
fun CarAdDomain.toCarAdData(): CarAd {
    return CarAd(
        userId = userId,
        adId = adId,
        city = city,
        brand = brand,
        model = model,
        realiseYear = realiseYear,
        price = price,
        body = body,
        typeEngine = typeEngine,
        transmission = transmission,
        drive = drive,
        description = description,
        condition = condition,
        engineCapacity = engineCapacity,
        steeringWheelSide = steeringWheelSide,
        mileage = mileage,
        color = color,
        imagesUrl = imagesUrl,
        timeMillis = timeMillis,
    )
}

/**
 * Конвертация [CarAd] в [CarAdDomain].
 *
 * @receiver Data-модель объявления
 * @return Domain-модель объявления
 */
fun CarAd.toCarAdDomain(): CarAdDomain {
    return CarAdDomain(
        userId = userId,
        adId = adId,
        city = city,
        brand = brand,
        model = model,
        realiseYear = realiseYear,
        price = price,
        body = body,
        typeEngine = typeEngine,
        transmission = transmission,
        drive = drive,
        description = description,
        condition = condition,
        engineCapacity = engineCapacity,
        steeringWheelSide = steeringWheelSide,
        mileage = mileage,
        color = color,
        imagesUrl = imagesUrl,
        timeMillis = timeMillis,
    )
}