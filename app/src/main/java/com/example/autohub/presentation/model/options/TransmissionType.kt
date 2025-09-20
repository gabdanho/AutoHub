package com.example.autohub.presentation.model.options

import com.example.autohub.presentation.constants.CarOptionsTags.AUTOMATIC_TAG
import com.example.autohub.presentation.constants.CarOptionsTags.MANUAL_TAG
import com.example.autohub.presentation.constants.CarOptionsTags.ROBOT_TAG
import com.example.autohub.presentation.constants.CarOptionsTags.VARIATOR_TAG
import com.example.autohub.presentation.model.StringResNamePresentation
import kotlinx.serialization.Serializable

/**
 * Enum для типа трансмиссии автомобиля.
 *
 * @property textRes Ресурс строки для отображения типа трансмиссии
 * @property tag Тег трансмиссии для хранения или передачи
 */
@Serializable
enum class TransmissionType(
    override val textRes: StringResNamePresentation,
    override val tag: String
) : CarOption  {
    MANUAL(textRes = StringResNamePresentation.LABEL_MANUAL, tag = MANUAL_TAG),
    AUTOMATIC(textRes = StringResNamePresentation.LABEL_AUTOMATIC, tag = AUTOMATIC_TAG),
    VARIATOR(textRes = StringResNamePresentation.LABEL_VARIATOR, tag = VARIATOR_TAG),
    ROBOT(textRes = StringResNamePresentation.LABEL_ROBOT, tag = ROBOT_TAG);

    companion object {
        /**
         * Создает [TransmissionType] по переданному тегу.
         *
         * @param value Тег трансмиссии
         * @return Enum значение или null, если тег не найден
         */
        fun fromTag(value: String?): TransmissionType? {
            return when(value) {
                MANUAL_TAG -> MANUAL
                AUTOMATIC_TAG -> AUTOMATIC
                VARIATOR_TAG -> VARIATOR
                ROBOT_TAG -> ROBOT
                else -> null
            }
        }
    }
}