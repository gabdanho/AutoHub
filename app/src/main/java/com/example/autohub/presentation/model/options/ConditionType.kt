package com.example.autohub.presentation.model.options

import com.example.autohub.presentation.constants.CarOptionsTagsConstants.BROKEN_TAG
import com.example.autohub.presentation.constants.CarOptionsTagsConstants.NOT_BROKEN_TAG
import com.example.autohub.presentation.constants.CarOptionsTagsConstants.NOT_WORKING_TAG
import com.example.autohub.presentation.model.StringResNamePresentation
import kotlinx.serialization.Serializable

/**
 * Enum для состояния автомобиля.
 *
 * @property textRes Ресурс строки для отображения состояния
 * @property tag Тег состояния для хранения или передачи
 */
@Serializable
enum class ConditionType(
    override val textRes: StringResNamePresentation,
    override val tag: String,
) : CarOption {
    NOT_BROKEN(textRes = StringResNamePresentation.LABEL_NOT_BROKEN, tag = NOT_BROKEN_TAG),
    BROKEN(textRes = StringResNamePresentation.LABEL_BROKEN, tag = BROKEN_TAG),
    NOT_WORKING(textRes = StringResNamePresentation.LABEL_NOT_WORKING, tag = NOT_WORKING_TAG);

    companion object {
        /**
         * Создает [ConditionType] по переданному тегу.
         *
         * @param value Тег состояния
         * @return Enum значение или null, если тег не найден
         */
        fun fromTag(value: String?): ConditionType? {
            return when(value) {
                NOT_BROKEN_TAG -> NOT_BROKEN
                BROKEN_TAG -> BROKEN
                NOT_WORKING_TAG -> NOT_WORKING
                else -> null
            }
        }
    }
}