package com.example.autohub.data

data class CarAd(
    val userUID: String = "",
    val dateAdPublished: String = "",
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
    val imagesUrl: List<String> = listOf()
)