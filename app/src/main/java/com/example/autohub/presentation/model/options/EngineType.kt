package com.example.autohub.presentation.model.options

import com.example.autohub.presentation.constants.CarOptionsTags.DIESEL_TAG
import com.example.autohub.presentation.constants.CarOptionsTags.HYBRID_TAG
import com.example.autohub.presentation.constants.CarOptionsTags.PETROL_TAG
import com.example.autohub.presentation.model.StringResNamePresentation
import kotlinx.serialization.Serializable

/**
 * Enum для типа двигателя автомобиля.
 *
 * @property textRes Ресурс строки для отображения типа двигателя
 * @property tag Тег двигателя для хранения или передачи
 */
@Serializable
enum class EngineType(
    override val textRes: StringResNamePresentation,
    override val tag: String
) : CarOption  {
    PETROL(textRes = StringResNamePresentation.LABEL_PETROL, tag = PETROL_TAG),
    DIESEL(textRes = StringResNamePresentation.LABEL_DIESEL, tag = DIESEL_TAG),
    HYBRID(textRes = StringResNamePresentation.LABEL_HYBRID, tag = HYBRID_TAG);

    companion object {
        /**
         * Создает [EngineType] по переданному тегу.
         *
         * @param value Тег типа двигателя
         * @return Enum значение или null, если тег не найден
         */
        fun fromTag(value: String?): EngineType? {
            return when(value) {
                PETROL_TAG -> PETROL
                DIESEL_TAG -> DIESEL
                HYBRID_TAG -> HYBRID
                else -> null
            }
        }
    }
}