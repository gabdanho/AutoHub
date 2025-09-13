package com.example.autohub.presentation.model.options

import com.example.autohub.presentation.constants.CarOptionsTagsConstants.LEFT_STEERING_WHEEL_TAG
import com.example.autohub.presentation.constants.CarOptionsTagsConstants.RIGHT_STEERING_WHEEL_TAG
import com.example.autohub.presentation.model.StringResNamePresentation
import kotlinx.serialization.Serializable

@Serializable
enum class SteeringWheelSideType(
    override val textRes: StringResNamePresentation,
    override val tag: String
) : CarOption  {
    RIGHT(textRes = StringResNamePresentation.LABEL_RIGHT_WHEEL, tag = RIGHT_STEERING_WHEEL_TAG),
    LEFT(textRes = StringResNamePresentation.LABEL_LEFT_WHEEL, tag = LEFT_STEERING_WHEEL_TAG);

    companion object {
        fun fromTag(value: String?): SteeringWheelSideType? {
            return when(value) {
                RIGHT_STEERING_WHEEL_TAG -> RIGHT
                LEFT_STEERING_WHEEL_TAG -> LEFT
                else -> null
            }
        }
    }
}