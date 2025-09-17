package com.example.autohub.domain.model.ad

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