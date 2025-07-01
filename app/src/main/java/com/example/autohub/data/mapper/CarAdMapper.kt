package com.example.autohub.data.mapper

import com.example.autohub.data.model.CarAdDto
import com.example.autohub.domain.model.CarAd

fun CarAd.toCarAdDto(): CarAdDto {
    return CarAdDto(
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

fun CarAdDto.toCarAd(): CarAd {
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