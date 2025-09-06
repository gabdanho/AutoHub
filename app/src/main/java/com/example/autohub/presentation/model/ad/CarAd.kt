package com.example.autohub.presentation.model.ad

import com.example.autohub.presentation.model.options.BodyType
import com.example.autohub.presentation.model.options.ConditionType
import com.example.autohub.presentation.model.options.DriveType
import com.example.autohub.presentation.model.options.EngineType
import com.example.autohub.presentation.model.options.SteeringWheelSideType
import com.example.autohub.presentation.model.options.TransmissionType
import kotlinx.serialization.Serializable

@Serializable
data class CarAd(
    val userUID: String = "",
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
    val city: String = ""
)