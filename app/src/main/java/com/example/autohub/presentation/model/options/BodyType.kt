package com.example.autohub.presentation.model.options

import com.example.autohub.presentation.CONVERTIBLE_TAG
import com.example.autohub.presentation.COUPE_TAG
import com.example.autohub.presentation.CROSSOVER_TAG
import com.example.autohub.presentation.HATCHBACK_TAG
import com.example.autohub.presentation.LIMOUSINE_TAG
import com.example.autohub.presentation.MINIVAN_TAG
import com.example.autohub.presentation.PICKUP_TRUCK_TAG
import com.example.autohub.presentation.SEDAN_TAG
import com.example.autohub.presentation.STATION_WAGON_TAG
import com.example.autohub.presentation.SUV_TAG
import com.example.autohub.presentation.VAN_TAG
import com.example.autohub.presentation.model.StringResNamePresentation

enum class BodyType(
    override val textRes: StringResNamePresentation,
    override val tag: String
) : CarOption {
    SEDAN(textRes = StringResNamePresentation.LABEL_SEDAN, tag = SEDAN_TAG),
    COUPE(textRes = StringResNamePresentation.LABEL_COUPE, tag = COUPE_TAG),
    CONVERTIBLE(textRes = StringResNamePresentation.LABEL_CONVERTIBLE, tag = CONVERTIBLE_TAG),
    STATION_WAGON(textRes = StringResNamePresentation.LABEL_STATION_WAGON, tag = STATION_WAGON_TAG),
    HATCHBACK(textRes = StringResNamePresentation.LABEL_HATCHBACK, tag = HATCHBACK_TAG),
    VAN(textRes = StringResNamePresentation.LABEL_VAN, tag = VAN_TAG),
    MINIVAN(textRes = StringResNamePresentation.LABEL_MINIVAN, tag = MINIVAN_TAG),
    SUV(textRes = StringResNamePresentation.LABEL_SUV, tag = SUV_TAG),
    CROSSOVER(textRes = StringResNamePresentation.LABEL_CROSSOVER, tag = CROSSOVER_TAG),
    PICKUP_TRUCK(textRes = StringResNamePresentation.LABEL_PICKUP_TRUCK, tag = PICKUP_TRUCK_TAG),
    LIMOUSINE(textRes = StringResNamePresentation.LABEL_LIMOUSINE, tag = LIMOUSINE_TAG);

    companion object {
        fun fromTag(value: String?): BodyType? {
            return when(value) {
                SEDAN_TAG -> SEDAN
                COUPE_TAG -> COUPE
                CONVERTIBLE_TAG -> CONVERTIBLE
                STATION_WAGON_TAG -> STATION_WAGON
                HATCHBACK_TAG -> HATCHBACK
                VAN_TAG -> VAN
                MINIVAN_TAG -> MINIVAN
                SUV_TAG -> SUV
                CROSSOVER_TAG -> CROSSOVER
                PICKUP_TRUCK_TAG -> PICKUP_TRUCK
                LIMOUSINE_TAG -> LIMOUSINE
                else -> null
            }
        }
    }
}