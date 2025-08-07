package com.example.autohub.data.mapper

import com.example.autohub.data.firebase.model.ad.CarAd
import com.example.autohub.domain.model.CarAd as CarAdDomain

fun CarAd.toCarAdData(): CarAd {
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