package com.example.autohub.presentation.model.options

import com.example.autohub.presentation.model.StringResNamePresentation

enum class BodyType(override val textRes: StringResNamePresentation) : CarOption {
    SEDAN(textRes = StringResNamePresentation.LABEL_SEDAN),
    COUPE(textRes = StringResNamePresentation.LABEL_COUPE),
    CONVERTIBLE(textRes = StringResNamePresentation.LABEL_CONVERTIBLE),
    STATION_WAGON(textRes = StringResNamePresentation.LABEL_STATION_WAGON),
    HATCHBACK(textRes = StringResNamePresentation.LABEL_HATCHBACK),
    VAN(textRes = StringResNamePresentation.LABEL_VAN),
    MINIVAN(textRes = StringResNamePresentation.LABEL_MINIVAN),
    SUV(textRes = StringResNamePresentation.LABEL_SUV),
    CROSSOVER(textRes = StringResNamePresentation.LABEL_CROSSOVER),
    PICKUP_TRUCK(textRes = StringResNamePresentation.LABEL_PICKUP_TRUCK),
    LIMOUSINE(textRes = StringResNamePresentation.LABEL_LIMOUSINE);
}