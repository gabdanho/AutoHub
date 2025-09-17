package com.example.autohub.data.mapper

import com.example.autohub.data.firebase.model.ad.CarAd
import com.example.autohub.domain.model.ad.CarAd as CarAdDomain

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