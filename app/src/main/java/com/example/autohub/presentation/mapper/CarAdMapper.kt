package com.example.autohub.presentation.mapper

import com.example.autohub.presentation.model.ad.CarAd
import com.example.autohub.domain.model.CarAd as CarAdDomain

fun CarAd.toCarAdDomain(): CarAdDomain {
    return CarAdDomain(
        userUID = userUID,
        adID = adID,
        dateAdPublished = dateAdPublished,
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
        imagesUrl = imagesUrl
    )
}

fun CarAdDomain.toCarAdPresentation(): CarAd {
    return CarAd(
        userUID = userUID,
        adID = adID,
        dateAdPublished = dateAdPublished,
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
        imagesUrl = imagesUrl
    )
}