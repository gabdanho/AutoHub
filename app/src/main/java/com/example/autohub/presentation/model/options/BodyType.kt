package com.example.autohub.presentation.model.options

import com.example.autohub.presentation.constants.CarOptionsTags.CONVERTIBLE_TAG
import com.example.autohub.presentation.constants.CarOptionsTags.COUPE_TAG
import com.example.autohub.presentation.constants.CarOptionsTags.CROSSOVER_TAG
import com.example.autohub.presentation.constants.CarOptionsTags.HATCHBACK_TAG
import com.example.autohub.presentation.constants.CarOptionsTags.LIMOUSINE_TAG
import com.example.autohub.presentation.constants.CarOptionsTags.MINIVAN_TAG
import com.example.autohub.presentation.constants.CarOptionsTags.PICKUP_TRUCK_TAG
import com.example.autohub.presentation.constants.CarOptionsTags.SEDAN_TAG
import com.example.autohub.presentation.constants.CarOptionsTags.STATION_WAGON_TAG
import com.example.autohub.presentation.constants.CarOptionsTags.SUV_TAG
import com.example.autohub.presentation.constants.CarOptionsTags.VAN_TAG
import com.example.autohub.presentation.model.StringResNamePresentation
import kotlinx.serialization.Serializable

/**
 * Enum для типа кузова автомобиля.
 *
 * @property textRes Ресурс строки для отображения типа кузова
 * @property tag Тег типа кузова для хранения или передачи
 */
@Serializable
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
        /**
         * Создает [BodyType] по переданному тегу.
         *
         * @param value Тег типа кузова
         * @return Enum значение или null, если тег не найден
         */
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