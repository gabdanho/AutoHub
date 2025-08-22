package com.example.autohub.presentation.navigation.model.nav_type

import com.example.autohub.presentation.model.ad.CarAd
import kotlinx.serialization.KSerializer

class CarAdNavType(serializer: KSerializer<CarAd> = CarAd.serializer())
    : NavTypeSerializer<CarAd>(
        serializer = serializer
    )