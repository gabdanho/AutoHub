package com.example.autohub.presentation.model.options

import com.example.autohub.presentation.constants.CarOptionsTagsConstants.LEFT_STEERING_WHEEL_TAG
import com.example.autohub.presentation.constants.CarOptionsTagsConstants.RIGHT_STEERING_WHEEL_TAG
import com.example.autohub.presentation.model.StringResNamePresentation
import kotlinx.serialization.Serializable

/**
 * Enum для стороны руля автомобиля.
 *
 * @property textRes Ресурс строки для отображения стороны руля
 * @property tag Тег стороны руля для хранения или передачи
 */
@Serializable
enum class SteeringWheelSideType(
    override val textRes: StringResNamePresentation,
    override val tag: String
) : CarOption  {
    RIGHT(textRes = StringResNamePresentation.LABEL_RIGHT_WHEEL, tag = RIGHT_STEERING_WHEEL_TAG),
    LEFT(textRes = StringResNamePresentation.LABEL_LEFT_WHEEL, tag = LEFT_STEERING_WHEEL_TAG);

    companion object {
        /**
         * Создает [SteeringWheelSideType] по переданному тегу.
         *
         * @param value Тег стороны руля
         * @return Enum значение или null, если тег не найден
         */
        fun fromTag(value: String?): SteeringWheelSideType? {
            return when(value) {
                RIGHT_STEERING_WHEEL_TAG -> RIGHT
                LEFT_STEERING_WHEEL_TAG -> LEFT
                else -> null
            }
        }
    }
}